mod r#mod;
use std::io::stdin;
use crate::r#mod::ImageHandler;

#[tokio::main]
async fn main() {
    println!("Skeleton Console");
    println!(" ");
    println!("Image Commands:");
    for image_command in vec![
        "image info <image_id> - View information about an image", "image launch <image_id> <template> <binded-port> <exposed-port> - Launch an image",
    ] {
        println!("- {image_command}");
    }

    let mut image_handler = ImageHandler {
        sub_commands: vec![]
    };

    loop {
        let mut name = String::new();
        stdin().read_line(&mut name).unwrap();

        if image_handler.subcommand_of(name.as_str()) {
            image_handler.parse_command_input(name.as_str(), name.split(" ").collect()).await.expect("Unable to handle command input");
        } else {
            println!("Unable to find this command!")
        }
    }
}