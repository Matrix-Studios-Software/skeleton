# Skeleton

Skeleton is an extensive and easy-to-use framework that supports creating, destroying, and managing servers using the Docker framework.


> [!WARNING]
> This framework has been loosely tested and probably will not work as expected.

# Features

Skeleton has many unique features which allows people to hand the management side of servers over to the framework to ensure not only server health but server reliability. Some of the features that we are currently working towards are:

- [ ] Automatic container deployment
- [ ] Customizable templates for launching servers 
- [ ] Health monitoring for containers and performance management
- [ ] Automatic scaling of resources within given range
- [ ] Load balancer for application load
- [ ] Web security and authorization management
- [ ] More to come!

# How To Use

In order to use skeleton, you must first make a template of the type of server you will be creating. First, you must navigate to the file location that you have defined in the configuration for skeleton. Once you do this, you will see a folder called `templates` that you will place your server files into.

Once you have completed the first step, you can create a file called `template-config.json` and set it up. An example JSON configuration would look like this:

```yaml
{
  id: "my-server",
  name: "MyServerName",
  command: "java -jar SomeApp.java",
  space: 2048 #in format of MB
}
```



