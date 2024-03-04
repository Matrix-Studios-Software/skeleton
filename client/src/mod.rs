#[derive(Clone)]
pub struct ImageHandler {
    pub sub_commands: Vec<String>
}

impl ImageHandler {
    pub fn parse_command_input(&mut self, name: &str, args: Vec<&str>) {
        let node = args.iter().nth(1);

        if node.is_some() {
            println!("Node: {}", node.unwrap())
        }
    }
}
