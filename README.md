<div align='center'>
    <h1>noper2</h1>
    <p>서울시 기존무허가 재개발 프로젝트입니다.</p>

<img src="https://img.shields.io/badge/Java-CC0000?style=flat&logo=java&logoColor=white"/>
<img src="https://img.shields.io/badge/SpringBoot-green?style=flat&logo=spring&logoColor=white"/>
<img src="https://img.shields.io/badge/Bootstrap-9933FF?style=flat&logo=bootstrap&logoColor=white"/>
<img src="https://img.shields.io/badge/Oracle SQL-FF0000?style=flat&logo=oracle&logoColor=white"/>
<img src="https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=html5&logoColor=white"/>
<img src="https://img.shields.io/badge/CSS-1572B6?style=flat&logo=css3&logoColor=white"/>
<img src="https://img.shields.io/badge/JavaScript-yellow?style=flat&logo=javascript&logoColor=white"/>
<img src="https://img.shields.io/badge/jQuery-3366CC?style=flat&logo=jquery&logoColor=white"/>
</div>
<div align='center'>
<img src="https://img.shields.io/badge/IntelliJ IDEA-000000?style=flat&logo=intellijidea&logoColor=white"/>
<img src="https://img.shields.io/badge/Eclipse IDE-660099?style=flat&logo=eclipseide&logoColor=white"/>
<img src="https://img.shields.io/badge/GitLab-FF6600?style=flat&logo=gitlab&logoColor=white"/>
</div>

## 💾 Prerequisite

**응용프로그램:**
* [Intellij](https://www.jetbrains.com/ko-kr/idea/download/?section=windows)
* [Eclipse](https://www.eclipse.org/downloads/)

**요구사항:**
* [Oracle OpenJDK 11](https://jdk.java.net/java-se-ri/11-MR2)

## 🗒 Project Execute
**Git Clone:**
```bash
git clone http://geonpf.iptime.org:60000/seoul/noper2.git
```

## 📎 Folder and File
`/main/java/kr/go/seoul/noper2`: 프로젝트 메인 경로

`/test/java/lxrdip/analyze`: 프로젝트 테스트 코드 경로

`/main/resources/mybatis`: Mybatis 설정 경로

`/main/resources/mybatis/mapper`: Mybatis XML 경로

`/main/resources/static`: JS, CSS 경로

`/main/resources/templates`: 화면 HTML 경로

## 📎 Packages
`/noper2/controller`: 컨트롤러 클래스 경로

`/noper2/controller/api`: API 컨트롤러 클래스 경로

`/noper2/domain`: 도메인 클래스 경로

`/noper2/domain/common`: 도메인의 공통으로 사용중인 필드를 갖고 있는 클래스 경로

`/noper2/dto`: 화면에서 데이터를 받을 때 사용되는 클래스 경로

`/noper2/global`: 공통적으로 사용되는 기능을 보관하는 패키지

`/noper2/global/config`: 설정 클래스 경로

`/noper2/global/error`: 에러 핸들러 클래스 경로

`/noper2/global/config`: 공통 기능 클래스 경로

## 📎 Thymeleaf
`/templates/component`: 콘텐츠 내부에 들어가는 공통기능 경로

`/templates/config`: ead 태그안에 들어가는 요소들 경로 (예: 라이브러리 혹은 meta 태그들)

`/templates/fragment`: header footer와 같이 기본적으로 들어가는 요소 경로

`/templates/layouts`: 요소들의 위치를 배치하는 HTML 경로

`/templates/fragment`: 콘텐츠 내용이 들어갈 HTML 경로