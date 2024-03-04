mod r#mod;
use std::io::stdin;
use crate::r#mod::ImageHandler;

fn main() {
    println!("Skeleton Console");
    let mut image_handler = ImageHandler {
        sub_commands: vec![]
    };

    loop {
        let mut name = String::new();
        stdin().read_line(&mut name).unwrap();

        image_handler.parse_command_input(name.as_str(), name.split(" ").collect());

        println!("Sent command: {}", name);
    }
}

fn get_image_info(id: &str) -> Result<(), Box<dyn std::error::Error>> {
    let res = reqwest::blocking::get("https://httpbin.org/get")?;

    println!("{}", res.status());

    let body = res.bytes()?;
    let v = body.to_vec();
    let s = String::from_utf8_lossy(&v);
    println!("response: {} ", s);

    Ok(())
}