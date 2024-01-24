// import { isClickableInput } from "@testing-library/user-event/dist/utils"
// import { useEffect } from "react"

// const SocialBtns = () => {
//     const login = new SocialLogin()

//     let isClicked = false

//     useEffect(() => {
//         login.setUpNaverLogin()
//     }, [])

//     const socialLogin = [
//         {
//             src: NaverLogo,
//             name: "네이버",
//             onclick: (e) => {
//                 e.prevenDefault()

//                 isClickableInput("naver")
//             }
//         },
//     ]

//     const clickBtn = async (type) => {
//         if (isClicked) {
//             return
//         } else {
//             isClicked = true
//             util.delay(1000).then(() => {
//                 isClicked = false
//             })
//         }

//         console.log('click')

//         switch (type) {
//             case 'naver':
//                 login.naverLogin()
//                 break
//         }
//     }

//     return (
//         <>
//             <div className="div_social">
//                 {socialLogin.map((e, i) => {
//                     return (<img
//                         key={`${e.name}`}
//                         onClick={e.onclick}
//                         src={e.src}
//                         alt=""
//                     ></img>)
//                 })}
//             </div>
//             <div id="naverIdLogin" style={{ display: 'none' }}></div>
//         </>
//     )
// }

// export default SocialBtns