importScripts("https://www.gstatic.com/firebasejs/8.10.0/firebase-app.js");
importScripts(
  "https://www.gstatic.com/firebasejs/8.10.0/firebase-messaging.js"
);

// Initialize Firebase
const firebaseConfig = {
        apiKey: "AIzaSyCxxUbccZ7bwVgT7rzA7yjcLCXQPnvnmDc",
        authDomain: "saessak-63104.firebaseapp.com",
        projectId: "saessak-63104",
        storageBucket: "saessak-63104.appspot.com",
        messagingSenderId: "115340242931",
        appId: "1:115340242931:web:390ff13b031281d17aac86",
        measurementId: "G-PPTD28SK2P"
};

firebase.initializeApp(firebaseConfig);
// Firebase Messaging 초기화
// Show Notification
// 메시지 기능 활성화를 알림
const messaging = firebase.messaging();

// 백그라운드
messaging.onBackgroundMessage(payload => {
    console.log('background');
    console.log(payload);
    handleDataMessage(payload.data);
});

// 포그라운드
self.addEventListener('message', (event) => {
    if(event.data && event.data.type === 'foreground') {
        console.log('foreground');
        handleDataMessage(event.data.payload);
    }
})

function handleDataMessage(data) {
    console.log('데이터 메시지 처리');

    const notificationTitle = data.title;
    const notificationOptions = {
        body: data.body
    }

    self.registration.showNotification(notificationTitle, notificationOptions);
}

// 푸시 버튼을 누를 때 알림을 띄우는 방식
//self.addEventListener('push', (event) => {
//  const options = {
//    body: event.data.json(),
//  };
//  const title = options.body.notification.title;
//  const body = options.body.notification.body;
//  console.log(title);
////   console.log(body);
//
//  event.waitUntil(
//      self.registration.showNotification(title, options.body.notification)
//  );
//});

// 클릭 이벤트 처리
self.addEventListener('notificationclick', event => {
  event.notification.close();
});
