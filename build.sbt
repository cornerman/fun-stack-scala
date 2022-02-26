import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

Global / onChangedBuildSource := ReloadOnSourceChanges

inThisBuild(Seq(
  organization := "io.github.fun-stack",
  scalaVersion := "2.13.8",

  licenses := Seq("MIT License" -> url("https://opensource.org/licenses/MIT")),

  homepage := Some(url("https://github.com/fun-stack/fun-stack-scala")),

  scmInfo := Some(ScmInfo(
    url("https://github.com/fun-stack/fun-stack-scala"),
    "scm:git:git@github.com:fun-stack/fun-stack-scala.git",
    Some("scm:git:git@github.com:fun-stack/fun-stack-scala.git"))
  ),

  pomExtra :=
    <developers>
      <developer>
        <id>jkaroff</id>
        <name>Johannes Karoff</name>
        <url>https://github.com/cornerman</url>
      </developer>
    </developers>,

  sonatypeCredentialHost := "s01.oss.sonatype.org",
  sonatypeRepository := "https://s01.oss.sonatype.org/service/local",
))

lazy val commonSettings = Seq(
  addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.2" cross CrossVersion.full),
  libraryDependencies ++=
    Deps.scalatest.value % Test ::
      Nil,
  scalacOptions --= Seq("-Xfatal-warnings", "-Wconf:any&src=src_managed/.*"),
)

lazy val jsSettings = Seq(
  useYarn := true,
  scalacOptions += {
    val githubRepo    = "fun-stack/fun-stack-scala"
    val local         = baseDirectory.value.toURI
    val subProjectDir = baseDirectory.value.getName
    val remote        = s"https://raw.githubusercontent.com/${githubRepo}/${git.gitHeadCommit.value.get}"
    s"-P:scalajs:mapSourceURI:$local->$remote/${subProjectDir}/"
  },
  scalacOptions += "-P:scalajs:nowarnGlobalExecutionContext", //TODO: setImmediate
)

lazy val core = project
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .in(file("core"))
  .settings(commonSettings, jsSettings)
  .settings(
    name := "fun-stack-core",
    libraryDependencies ++=
        Deps.chameleon.value ::
        Nil,
  )

lazy val backend = project
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .in(file("backend"))
  .dependsOn(core)
  .settings(commonSettings, jsSettings)
  .settings(
    name := "fun-stack-backend",
    libraryDependencies ++=
      Deps.cats.effect.value ::
        Deps.awsSdkJS.sns.value ::
        Deps.awsSdkJS.cognitoidentityprovider.value ::
        Deps.sloth.value ::
        Deps.mycelium.core.value ::
        Nil,
  )

lazy val lambdaEventAuthorizer = project
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .dependsOn(core)
  .in(file("lambdaEventAuthorizer"))
  .settings(commonSettings, jsSettings)
  .settings(
    name := "fun-stack-lambda-event-authorizer",
    libraryDependencies ++=
      Deps.cats.effect.value ::
        Deps.awsSdkJS.sns.value ::
        Deps.awsSdkJS.lambda.value ::
        Deps.awsLambdaJS.value ::
        Deps.sloth.value ::
        Deps.mycelium.core.value ::
        Nil,

    // The aws-sdk is provided in lambda environment.
    // Not depending on it explicitly makes the bundle size smaller.
    // But we do not know whether our facades are on the correct version.
    /* Compile / npmDependencies ++= */
    /*   NpmDeps.awsSdk :: */
    /*   Nil */
  )

lazy val lambdaHttpTapir = project
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .dependsOn(core)
  .in(file("lambdaHttpTapir"))
  .settings(commonSettings, jsSettings)
  .settings(
    name := "fun-stack-lambda-http-tapir",
    libraryDependencies ++=
      Deps.cats.effect.value ::
        Deps.awsSdkJS.lambda.value ::
        Deps.awsLambdaJS.value ::
        Deps.sttp.core.value ::
        Deps.sttp.circe.value ::
        /* Deps.sttp.openApi.value :: */
        /* Deps.sttp.circeOpenApi.value :: */
        Nil,

    // The aws-sdk is provided in lambda environment.
    // Not depending on it explicitly makes the bundle size smaller.
    // But we do not know whether our facades are on the correct version.
    /* Compile / npmDependencies ++= */
    /*   NpmDeps.awsSdk :: */
    /*   Nil */
  )

/* lazy val lambdaHttp = project */
/*   .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin) */
/*   .dependsOn(core) */
/*   .in(file("lambdaHttp")) */
/*   .settings(commonSettings, jsSettings) */
/*   .settings( */
/*     name := "fun-stack-lambda-http", */
/*     libraryDependencies ++= */
/*       Deps.cats.effect.value :: */
/*         Deps.awsSdkJS.lambda.value :: */
/*         Deps.awsLambdaJS.value :: */
/*         /1* Deps.sttp.openApi.value :: *1/ */
/*         /1* Deps.sttp.circeOpenApi.value :: *1/ */
/*         Nil, */

/*     // The aws-sdk is provided in lambda environment. */
/*     // Not depending on it explicitly makes the bundle size smaller. */
/*     // But we do not know whether our facades are on the correct version. */
/*     /1* Compile / npmDependencies ++= *1/ */
/*     /1*   NpmDeps.awsSdk :: *1/ */
/*     /1*   Nil *1/ */
/*   ) */

lazy val lambdaWs = project
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .dependsOn(core)
  .in(file("lambdaWs"))
  .settings(commonSettings, jsSettings)
  .settings(
    name := "fun-stack-lambda-ws",
    libraryDependencies ++=
      Deps.sloth.value ::
        Deps.cats.effect.value ::
        Deps.mycelium.core.value ::
        Deps.awsSdkJS.lambda.value ::
        Deps.awsLambdaJS.value ::
        Nil,

    // The aws-sdk is provided in lambda environment.
    // Not depending on it explicitly makes the bundle size smaller.
    // But we do not know whether our facades are on the correct version.
    /* Compile / npmDependencies ++= */
    /*   NpmDeps.awsSdk :: */
    /*   Nil */
  )

lazy val web = project
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .dependsOn(core)
  .in(file("web"))
  .settings(commonSettings, jsSettings)
  .settings(
    name := "fun-stack-web",
    libraryDependencies ++=
      Deps.sloth.value ::
        Deps.cats.effect.value ::
        Deps.colibri.value ::
        /* Deps.jsTime.value :: */
        Deps.sttp.jsClient.value ::
        Deps.sttp.catsClient.value ::
        Deps.mycelium.clientJs.value ::
        Nil,
    Compile / npmDependencies ++=
      NpmDeps.jwtDecode ::
        Nil,
  )
