FROM eclipse-temurin:25-jdk

WORKDIR app/

COPY . .

RUN find src -name "*.java" > list.txt
RUN javac \
	-cp "src:postgresql-42.7.13.jar:peer-at-code-framework-1.2.0.jar:Treasure.jar" \
	-processorpath TreasureProcessor.jar \
	-processor dev.peerat.mapping.TreasureProcessor \
	-Atreasure.dependencies="postgresql-42.7.13.jar:peer-at-code-framework-1.2.0.jar:Treasure.jar" \
	-Atreasure.source="src" \
	-Aoutput=".apt_generated" \
	-Atreasure.java.version=25 \
	-Atreasure.java.args.jvm="-parameters" \
	-Atreasure.providers=dev.peerat.mapping.providers.postgresql.PostgreSQLProvider \
	@list.txt

EXPOSE 8001

ENTRYPOINT ["java", "-cp", ".apt_generated:postgresql-42.7.13.jar:peer-at-code-framework-1.2.0.jar:Treasure.jar","be.jeffcheasey88.codetaskfollower.Main"]