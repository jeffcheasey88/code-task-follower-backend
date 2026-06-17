FROM eclipse-temurin:25-jdk

WORKDIR app/

COPY . .

RUN find src -name "*.java" > list.txt
RUN javac \
	-cp "src:mysql-connector-j-9.4.0.jar:peer-at-code-framework-1.2.0.jar:Treasure.jar:TreasureProcessor.jar" \
	-processorpath TreasureProcessor.jar \
	-processor dev.peerat.mapping.TreasureProcessor \
	-Atreasure.dependencies="mysql-connector-j-9.4.0.jar:peer-at-code-framework-1.2.0.jar:Treasure.jar" \
	-Atreasure.source="src" \
	-Aoutput=".apt_generated" \
	-Atreasure.java.version=25 \
	-Atreasure.providers=dev.peerat.mapping.providers.mysql.MySQLProvider \
	@list.txt

EXPOSE 8001

ENTRYPOINT ["java", "-cp", ".apt_generated:mysql-connector-j-9.4.0.jar:peer-at-code-framework-1.2.0.jar:Treasure.jar","be.jeffcheasey88.codetaskfollower.Main"]