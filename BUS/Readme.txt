IDE는 IntelliJ IDEA Ultimate 2018.3을 사용하였습니다.

src/main/java/com/에 코드가 구현되어 있습니다.
데이터 생성부터 질의, 답변 까지 한 번에 실행됩니다.

mysql.auth는 DBP_LECTURE_DB 스키마를 위한 DB 접속정보 파일입니다.

Intellij에서 한글 사용을 위해서 build.Gradle에는

compileJava.options.encoding = 'UTF-8'

tasks.withType(JavaCompile) {
    options.encoding = 'UTF-8'
}

를,

Intellij의 vmoption에는
-Dfile.encoding=UTF-8
를 추가하였습니다.