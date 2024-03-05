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

        if image_handler.subcommand_of(name.as_str()) {
            image_handler.parse_command_input(name.as_str(), name.split(" ").collect());
        } else {
            println!("Unable to find this command!")
        }
    }
}