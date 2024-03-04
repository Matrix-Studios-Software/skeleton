use std::io::stdin;

fn main() {
    println!("Skeleton Console");
    loop {
        let mut name = String::new();
        stdin().read_line(&mut name).unwrap();

        if name.contains("image info") {
            let image_id = name.split(" ").nth(2);

            if image_id.is_none() {
                println!("Usage: image info <image_id>")
            } else {
                println!("Sending image info command...");
                get_image_info("idk").expect("Unable to handle request");
            }
        }

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
