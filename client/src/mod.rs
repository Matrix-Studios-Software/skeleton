use reqwest::Error;
use serde::ser::{Serialize, SerializeStruct, Serializer};

#[derive(Clone)]
pub struct ImageHandler {
    pub sub_commands: Vec<String>
}

pub struct DeploymentRequest {
    pub template_id: String,
    pub exposed_port: u32,
    pub binded_port: u32,
    pub host_name: String
}
impl Serialize for DeploymentRequest {
    fn serialize<S>(&self, serializer: S) -> Result<S::Ok, S::Error>
        where
            S: Serializer,
    {
        let mut s = serializer.serialize_struct("DeploymentRequest", 4)?;
        s.serialize_field("templateId", &self.template_id)?;
        s.serialize_field("hostName", &self.host_name)?;
        s.serialize_field("bindedPort", &self.binded_port)?;
        s.serialize_field("exposedPort", &self.exposed_port)?;
        s.end()
    }
}

impl ImageHandler {
    pub fn subcommand_of(&mut self, name: &str) -> bool {
        return name.starts_with("image")
    }

    pub async fn parse_command_input(&mut self, name: &str, args: Vec<&str>) -> Result<(), Error>{
        let node = args.iter().nth(1);

        if node.is_some() {
            let node_string = node.unwrap();

            match node_string.to_lowercase().as_str() {
                "info" => self.get_image_info(args.iter().nth(2).unwrap()),
                "launch" => Ok(self.launch_image(args.iter().nth(2).unwrap(), 25565, 25565, "test").await?),
                _ => Ok(())
            }.expect("Unable to handle web request")
        }

        return Ok(())
    }
    pub fn get_image_info(&mut self, id: &str) -> Result<(), Box<dyn std::error::Error>> {
        let res = reqwest::blocking::get("http://localhost:6969/deployment/images/".to_owned() + id)?;

        println!("{}", res.status());

        let body = res.bytes()?;
        let v = body.to_vec();
        let s = String::from_utf8_lossy(&v);
        println!("response: {} ", s);

        Ok(())
    }

    async fn launch_image(&mut self, id: &str, port: u32, bind: u32, template_id: &str) -> Result<(), Error> {
        let client = reqwest::Client::new();
        let deployment_request = DeploymentRequest {
            template_id: template_id.to_string(),
            exposed_port: port,
            binded_port: bind,
            host_name: "0.0.0.0".parse().unwrap()
        };

        let response = client.post("http://localhost:6969/deployment/images/".to_owned() + id + "/launch")
            .body(serde_json::to_string(&deployment_request).unwrap())
            .header("Content-Type", "application/json")
            .send().await?;

        println!("Response: {}", response.status());
        println!("{}", response.text().await?);

        Ok(())
    }
}
