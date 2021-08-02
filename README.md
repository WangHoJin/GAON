# 프로젝트 명

## 일정표

```mermaid
gantt
    dateFormat  MM-DD
    title       GAON 일정표
    %% excludes    weekends
    %% (`excludes` accepts specific dates in YYYY-MM-DD format, days of the week ("sunday") or "weekends", but not the word "weekdays".)

    section 프로젝트 준비
    주제 선정 및 스켈레톤 코드 분석	:done, 07-12, 5d
    기능 목록 상세 도출		   :done, 07-19, 1d
    화면 기획				  :done,07-20, 2d
   	Jira 등록		: done, 07-22, 1d
    ERD 작성		: done, 07-23, 1d
    역할 분배 및 사전 공부		: done, 07-24, 2d
   
    
	  section 회원 관리
    [F] 로그인/로그아웃		: done, 07-26, 4d
    [B] 로그인/로그아웃		: done, 07-26, 4d
    [F] 가입, 수정, 탈퇴		 : done, 07-26, 5d
    [B] 가입, 수정, 탈퇴		 : done, 07-26, 5d
     
   section 백앤드 설정
    AWS 설정		:done, 07-26, 2d
    AWS 도커 연동 : done, 07-27, 2d
    도커, WEB RTC, Socket 환경 설정		 : active, 07-28, 3d

    section 채널 관리
    [F] 방 만들기 	: active, ch1, 07-30, 4d
    [B] 방 만들기 	: active, ch1, 07-29, 4d
    [F] 방 초대, 방 참여   :  ch2, after ch1, 3d
    [B] 방 초대, 방 참여   :  ch2, after ch1, 3d
   
    게시판 : active, 08-02, 3d
    
    section Web RTC
    화상회의 : rtc1, 08-03, 3d
    채팅     :rtc2, after rtc1, 2d
    출석체크   : rtc3, after rtc2, 2d
    접속현황  : rtc4, after rtc3, 2d
    상단 플로팅 알람 : rtc5, after rtc4, 1d
    개인 알람 보내기 : after rtc5, 1d
    
    section 산출물
    시퀸스 다이어그램 작성   :07-28, 1d
    README 작성  			:07-28, 1d
    QA 및 버그 찾기  	  :08-01, 2d
    
    section 기타
    UCC제작 :08-10, 7d
    발표준비 :08-17, 3d
```

## ERD 
<div align="center"> 
![ERD](/uploads/2e323de6da887834ccf7cf2a6471edfe/ERD.png)
</div>

## 목차

- [프로젝트 소개](#프로젝트-소개)   
- [텍스트 ... ](#프로젝트-소개)   
- [프로젝트 명세](#프로젝트-명세)
  - [배포 환경](#배포-환경)
  - [개발 환경](#개발-환경)
  - [Design Resources](#design-resources)
  - [핵심 라이브러리](#핵심-라이브러리)
<br>

## 프로젝트 소개
소개 입력
<br>

## 프로젝트 명세
### 배포 환경
- __URL__ : 
- __배포 여부__ : O / X
- __접속 가능__ : 접속 가능 / 수정 중
- __HTTPS 적용__ : O / X
- __PORT__ : // 3rd Party에서 사용하는 포트가 있다면 기입해주세요. <- 기입 후 해당 주석 삭제
<br>

### 개발 환경
#### Front-end / Android
- __Framework__ : Vue.js / React / Android / Unity
- __지원 환경__ : Web / Mobile / Web App / Native App
- __담당자__ : 팀원 A, 팀원 B,
<br>

#### Back-end
- __Framework__ : Spring boot / Node.js / Django / Flask
- __Database__ : MySQL / MariaDB
- __담당자__ : 팀원 A, 팀원 B,
<br>

#### Design
- __Framework 사용__ : O / X
  - [Vuetify](https://vuetifyjs.com/)
  - [Element Plus](https://element-plus.org/)
- __Design Tool 사용__ : Adobe XD, Photoshop, Blender, Zeplin 등 (없을 경우 빈칸)
- __담당자__ : 팀원 A, 팀원 B,
<br>

### Design Resources
__외부 템플릿 또는 에셋__ (이미지 또는 링크 첨부)
- 무료 이미지, 아이콘, 폰트 등은 제외
- [Vue Argon Design System](https://www.creative-tim.com/product/vue-argon-design-system?affiliate_id=116187) (무료)
  - __사용 기능__ : 디자인 전반 적용
- [Vue Black Dashboard Pro](https://www.creative-tim.com/product/vue-black-dashboard-pro?affiliate_id=116187) (유료)
  - __사용 기능__ : 캘린더 컴포넌트 사용
<br>

__자체 제작 산출물__ (필요시 이미지 또는 설명 첨부)
- LOGO
- CardView
- Button
- Calendar
<br>

### 핵심 라이브러리
기본 제공하는 라이브러리 외 핵심 기능 구현에 사용한 라이브러리가 있다면 작성해주세요.   
예시 ) VR/AR 라이브러리, 애니메이션 라이브러리, 텍스트/사진/동영상 지원, 편집 라이브러리 등

- __AR CORE__
  - __링크__ : https://developers.google.com/ar
  - __소개__ : 구글에서 제공하는 AR 지원 라이브러리
  - __사용 기능__ : 이미지 인식, 이미지 위 영상 표시
  - __담당자__ : 팀원 A, 

- __COLOR THIEF__
  - __링크__ : https://lokeshdhakar.com/projects/color-thief/
  - __소개__ : 이미지에서 색상을 추출해주는 라이브러리
  - __사용 기능__ : 커버 사진에서 색상 추출 -> 배경 색상 변경
  - __담당자__ : 팀원 A,

- __Animate.css__
  - __링크__ : https://animate.style/
  - __소개__ : css 애니메이션 지원 라이브러리
  - __사용 기능__ : 메인 페이지 진입 시 애니메이션 사용
  - __담당자__ : 팀원 A,,


