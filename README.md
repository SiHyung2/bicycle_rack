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
===========================================================================================================================================================================================
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
