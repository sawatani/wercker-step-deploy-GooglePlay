type sbt || (
	sudo apt-get update -y
	sudo apt-get install -y apt-transport-https
	echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list
	sudo apt-get update -y
	sudo apt-get install -y sbt
)

export SBT_OPTS="-XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:PermSize=256M -XX:MaxPermSize=512M -Xss1M"
sbt --version
