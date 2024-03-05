#[derive(Clone)]
pub struct ImageHandler {
    pub sub_commands: Vec<String>
}

impl ImageHandler {
    pub fn subcommand_of(&mut self, name: &str) -> bool {
        return name.starts_with("image")
    }

    pub fn parse_command_input(&mut self, name: &str, args: Vec<&str>) {
        let node = args.iter().nth(1);

        if node.is_some() {
            let node_string = node.unwrap();

            match node_string.to_lowercase().as_str() {
                "info" => self.get_image_info(args.iter().nth(2).unwrap()),
                _ => Ok(())
            }.expect("Unable to handle web request");
        }
    }
    pub fn get_image_info(&mut self, id: &str) -> Result<(), Box<dyn std::error::Error>> {
        let res = reqwest::blocking::get("https://httpbin.org/get")?;

        println!("{}", res.status());

        let body = res.bytes()?;
        let v = body.to_vec();
        let s = String::from_utf8_lossy(&v);
        println!("response: {} ", s);

        Ok(())
    }
}
