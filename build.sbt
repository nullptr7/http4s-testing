

name := "http4s-testing"

version := "0.1"

scalaVersion := "2.13.6"

idePackagePrefix := Some("com.github.nullptr7")

lazy val global = project
  .in(file("."))
  .settings(
    name := "global",
    libraryDependencies ++= commonDependencies
  )
  .disablePlugins(AssemblyPlugin)

/*lazy val common = project
  .settings(
    name := "common",
    settings,
    libraryDependencies ++= commonDependencies
  )
  .disablePlugins(AssemblyPlugin)
  .enablePlugins()*/

/*lazy val bookService = project
  .settings(
    name := "book-service",
    settings,
    assemblySettings,
    libraryDependencies ++= commonDependencies
  )
  .dependsOn(
    common
  )*/

/*lazy val authorService = project
  .settings(
    name := "author-service",
    settings,
    assemblySettings,
    libraryDependencies ++= commonDependencies
  )
  .dependsOn(
    common
  )*/

//lazy val settings =
//  commonSettings
//wartremoverSettings
//scalafmtSettings

lazy val compilerOptions = Seq(
  "-unchecked",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-deprecation",
  "-encoding",
  "utf8"
)

lazy val commonSettings = Seq(
  scalacOptions ++= compilerOptions,
  resolvers ++= Seq(
    "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots"),
    "confluent" at "https://packages.confluent.io/maven/",
    "jitpack" at "https://jitpack.io"
  )
)

/*lazy val scalafmtSettings =
  Seq(
    scalafmtOnCompile := true,
    scalafmtTestOnCompile := true,
    scalafmtVersion := "1.2.0"
  )*/

lazy val assemblySettings = Seq(
  assembly / assemblyJarName := name.value + ".jar",
  assembly / assemblyMergeStrategy := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case "application.conf"            => MergeStrategy.concat
    case x                             =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  }
)

lazy val doobieVersion    = "1.0.0-RC1"
lazy val http4sVersion    = "1.0.0-M29"
lazy val circeVersion     = "0.15.0-M1"

lazy val commonDependencies = Seq(
  "org.tpolecat" %% "doobie-core" % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  //"org.tpolecat"  %% "doobie-specs2"       % doobieVersion,
  "org.postgresql" % "postgresql" % "42.2.24",
  "org.slf4j" % "slf4j-api" % "1.7.32",
  "org.slf4j" % "slf4j-simple" % "1.7.32",
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  //  "org.http4s"              %% "http4s-testing"                 % "0.21.24",
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  //  "org.typelevel"           %% "cats-core"                      % "2.6.1",
  //  "org.typelevel"           %% "cats-effect"                    % "2.5.1",
  //  "org.mockito"             %% "mockito-scala"                  % "1.16.37",
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.circe" %% "circe-literal" % circeVersion,
  "org.typelevel" %% "log4cats-slf4j" % "2.1.1"
)
