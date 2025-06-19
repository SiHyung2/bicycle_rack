## 실행 방법 ##

1. MainActiviy에서 clien-id부분에 자신의 네이버 AI API - client id를 삽입한다
2. 에뮬레이터에서 기본언어를 한글로 설정한다


## 핵심 구조 ##

src:.
|
├─java
│  └─com
│      └─example
│          └─myapplication
│              │  DataLoader.java
│              │  MainActivity.java
│              │
│              └─ui
│                  │  BikeInfoBottomSheet.java
│                  │  BottomSheet.java
│                  │
│                  └─BikeComponent
│                          BikeAdapter.java
│                          BikeInfo.java
│                          BikeRack.java
│                          BikeViewHolder.java
│
└─res
    ├─layout
    │      activity_main.xml
    │      bike_info_bottom_sheet.xml
    │      bottom_sheet.xml
    ├─raw
    └─     data.csv



**검색 원리**
- activity_main.java
  - setupSearchListener()

**자전거 보관대 파싱**
1. 자전거 보관대 엑셀 파일
  - raw>data.csv
2. 자전거 보관대 파싱 객체
  - BikeRack객체
3. 자전거 보관대 파싱 클래스
  - DataLoader.java에서 BikeRack객체를 가져와서 저장한다

**자전거 보관대 마커**
- 자전거 마커 표시하기
  - bikeRacks = DataLoader.readExcel(this);
  - Clusterer.Builder
  - leafMarkerUpdater
- UI의 마커에서 bikeRack의 위치 찾기
  - LatLng position = marker.getPosition();

**상태 관리**
- BikeAdapter.java에서
- BikeViewHolder.java



## 프리뷰 ##

**1-앱시작화면**

<img width="176" alt="Image" src="https://github.com/user-attachments/assets/32b4353e-8bd6-418f-a371-c70fb92d8580" />

<hr/>

**2-선택한 마커 표시**

<img width="179" alt="Image" src="https://github.com/user-attachments/assets/3b87eb64-41f9-43ea-898e-fc4dcff58b6c" />

<hr/>

**3-검색기능(경북대, 대구역 검색)**

<img width="181" alt="Image" src="https://github.com/user-attachments/assets/4ce2659c-88f2-4b55-a864-258c0d037b2d" />
<img width="178" alt="Image" src="https://github.com/user-attachments/assets/69fca493-5621-45f3-8ff2-0de8985db04f" />

<hr/>

**4-필터 기능(중구 검색&공기주입기)**

<img width="179" alt="Image" src="https://github.com/user-attachments/assets/ac6789f8-9976-4444-9dc6-75c586f5d3b2" />

<hr/>

**5-거치위치 저장**

<img width="177" alt="Image" src="https://github.com/user-attachments/assets/177bf506-49a5-48f9-a797-5e4d87cecc4a" />
<img width="179" alt="Image" src="https://github.com/user-attachments/assets/8d4a264b-221c-492f-b3fe-110ab80d9172" />

<hr/>

## 핵심 기능 ##
**1. 네이버 지도 API 연동**
  - 리프마커
  - 현재 내위치 표시
    
**2. 공공데이터 파싱(정적 파일)**
 1. 자전거 보관대 정보 읽기
 2. bikeRack객체에 담아서 하나하나 읽음
 3. bikeRackList에 bikeRack을 담음
    
**3. 마커 클릭 시 보관소 정보 표시**

**4. 검색 기능 : 보관소명, 주소명 입력**
  - 공기주입기여부도 필터링 가능
    
**5. 거치위치 저장**
  - 내가 자전거 거치한 위치를 저장한다 (캐시 기반 기능)

### 네이버 지도 API 연결법 ###

0. ncp에서 service -> AI,NAVER API 등록(앱수준 build.gradle안의 앱 주소를 입력해야함!)(maps API가 아님!)
1. build.gradle(프로젝트 수준)에 저장소 설정 추가
2. build.gradle(앱 수준)에 네이버 지도 SDK 의존성 추가
3. AndroidManifest.xml에 메타데이터안에 value값에 client id를 넣는다
4. 레이아웃에서 MapView를 사용해서띄움
5. activity에서 라이프 사이클을 호출

**참고 사이트 주소**
+ https://goodbegunishalfdone.tistory.com/entry/Android-%EC%A0%81%EC%9A%A9-%EB%84%A4%EC%9D%B4%EB%B2%84-%EC%A7%80%EB%8F%84-API1-%EC%A7%80%EB%8F%84-%EB%9D%84%EC%9A%B0%EA%B8%B0withmapView
+ https://goodbegunishalfdone.tistory.com/entry/Android-%EC%A0%81%EC%9A%A9-%EB%84%A4%EC%9D%B4%EB%B2%84-%EC%A7%80%EB%8F%84-API1-%EC%A7%80%EB%8F%84-%EB%9D%84%EC%9A%B0%EA%B8%B0withmapView


**주의점**
+ 블로그, 공식 사이트에서 업데이트된 내용을 제대로 입력하지않았음! 다음은 업데이트된 내용임
+ 업데이트로 인해서 프로젝트 수준의 build.gradle이 아닌 setting.gradle.kts에 다음을 입력한다
  + maven("https://repository.map.naver.com/archive/maven")

### 추가 ###
기존 ui부분에 지도 API를 추가했습니다.
기존 manifast에 client id를 넣는 부분을 코드 안에 넣어 간소화했습니다

```
    public void onMapReady(@NonNull NaverMap naverMap) {
        Marker marker = new Marker();
        marker.setPosition(new LatLng(37.5666102, 126.9783881)); // 서울시청 좌표
        marker.setMap(naverMap);
        marker.setOnClickListener(overlay -> {
            openSheet();
            return true;
        });
    }
```
이 부분에서 지도 다루는 부분을 넣으면 됩니다.
