# sdk-scala

SDK in scala for working with the fun-stack infrastructure.

## Links

Example on how to use it:
- Fun Scala Template: [example](https://github.com/fun-stack/example)

Terraform module for the corresponding AWS infrastructure:
- Fun Terraform Module: [terraform-aws-fun](https://github.com/fun-stack/terraform-aws-fun) (version `>= 0.6.0`)

See local development module for mocking the AWS infrastructure locally:
- Fun Local Environment: [local-env](https://github.com/fun-stack/local-env) (version `>= 0.3.0`)

## Get started

Get latest release:
```scala
libraryDependencies += "com.github.fun-stack" %%% "fun-stack-client-web" % "0.9.0"
libraryDependencies += "com.github.fun-stack" %%% "fun-stack-client-node" % "0.9.0"
libraryDependencies += "com.github.fun-stack" %%% "fun-stack-lambda-http-api-tapir" % "0.9.0"
libraryDependencies += "com.github.fun-stack" %%% "fun-stack-lambda-http-rpc" % "0.9.0"
libraryDependencies += "com.github.fun-stack" %%% "fun-stack-lambda-ws-rpc" % "0.9.0"
libraryDependencies += "com.github.fun-stack" %%% "fun-stack-lambda-ws-event-authorizer" % "0.9.0"
libraryDependencies += "com.github.fun-stack" %%% "fun-stack-backend" % "0.9.0"
```
