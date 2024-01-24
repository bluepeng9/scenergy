// import util from "../util";

// export default class SocialLogin {
//     naverLogin(value=true) {
//         const src = ""; //sdk

//         let naverScript = util.checkScript('naver', src, openPopup)

//         function openPopup() {
//             const naverLogin = new naver.LoginWitNaverId({
//                 clientID: process.env.Naver_Client_Id,
//                 callbackUrl: "",
//                 isPopup: false,
//                 loginButton: { color: "green", type: 3, height: 60 },
//                 callbackHandle: true
//             });

//             naverLogin.init();

//             console.log({ naverLogin, naver })
            
//             if (value) {
//                 const btn = document.getElementById('naverIdLogin').firstChild
//                 btn.click()

//                 return
//             }
            
//             naverLogin.getLoginStatus(function (status) {
//                 if (status) {
//                     const { email, name } = naverLogin.user
                    
//                     console.log({ email, name });

//                     if (email === undefined || email === null || name === undefined || name === null) {
//                         alert("이메일은 필수정보입니다. 정보제공을 동의해주세요");
//                         naverLogin.repromt();
//                         return;
//                     }
//                     util.removeScript(naverScript)
//                 } else {
//                     alert("callback 처리 실패");
//                 }
//             })
//         }

//     }
// }