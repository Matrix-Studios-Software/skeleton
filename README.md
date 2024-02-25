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
    "id": "my-server",
    "name": "MyServerName",
    "space": 2048 #in MB
}
```

This configuration file is just the bare version of the configuration. If you want to enable replications and configure how the server will replication you must make a settings file for the replications.

You can do this by creating a new file called `replication-settings.json` and setting it up using the 3 fields that are shown below:

```yaml
{
  maximumReplications: 10,
  minimumReplications: 2,
  replicationRate: 1
}
```

This will set an upper bound, `maximumRepications`, that the service will never exceed when launching new servers. It will also set a floor that the replications can never drop below for required services.

If you wanted to have multiple replications per invocation, you just change `replicationRate` to the amount you want. After this the server will identify the template and use it accordingly!

# Deploying

We deploy instances by web requests. The route you must use is `http://your-host:your-port/deployment/images/{id}/launch` You must also provide a `DeploymentTemplate` in the request body:
```yaml
{
  templateId: "my-server",
  exposedPort: 25565,
  bindedPort: 25572,
  hostName: "0.0.0.0"
}
```

This will launch an instance of `my-server` on the port `25572` with the host `0.0.0.0`.


