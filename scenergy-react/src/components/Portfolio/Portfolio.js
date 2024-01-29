/* 상단 사진크기 고정인 코드*/
import React, { useEffect, useState } from 'react';
import gsap, { Power1 } from 'gsap';
import ScrollToPlugin from 'gsap/ScrollToPlugin';

import { Link } from "react-router-dom";
import "./Portfolio.css";

gsap.registerPlugin(ScrollToPlugin);

const Portfolio = () => {
    const [cards, setCards] = useState([
        { id: 1, musicianName: "", profileImage: null, bio: "" },
        { id: 2, musicianName: "", profileImage: null, videos: {} },
        { id: 3, musicianName: "", profileImage: null, contact: { email: "", phone: "" } },
        { id: 4, musicianName: "", profileImage: null, bio: "" },
    ]);

    const handleNameChange = (event, cardId) => {
        const updatedCards = cards.map(card =>
            card.id === cardId ? { ...card, musicianName: event.target.value } : card
        );
        setCards(updatedCards);
    };

    const handleImageChange = (event, cardId) => {
        if (cardId === 1) {
            const selectedImage = event.target.files[0];
            const updatedCards = cards.map(card =>
                card.id === cardId ? { ...card, profileImage: URL.createObjectURL(selectedImage) } : card
            );
            setCards(updatedCards);
        }
    };

    const handleBioChange = (event, cardId) => {
        const updatedCards = cards.map(card =>
            card.id === cardId ? { ...card, bio: event.target.value } : card
        );
        setCards(updatedCards);
    };

    const handleVideoChange = (event, cardId, videoIndex) => {
        const selectedVideo = event.target.files[0];
        if (cardId === 2) {
            const updatedCards = cards.map(card =>
                card.id === cardId ? { ...card, videos: { ...card.videos, [videoIndex]: URL.createObjectURL(selectedVideo) } } : card
            );
            setCards(updatedCards);
        }
    };

    const handleContactChange = (event, cardId, field) => {
        const updatedCards = cards.map(card =>
            card.id === cardId ? { ...card, contact: { ...card.contact, [field]: event.target.value } } : card
        );
        setCards(updatedCards);
    };

    useEffect(() => {
        const $window = window;
        const scrollTime = 1.2;
        const scrollDistance = 170;

        $window.addEventListener("wheel", function (event) {
            event.preventDefault();

            const delta = event.deltaY / 120 || -event.detail / 3;
            const scrollTop = $window.scrollY;
            const finalScroll = scrollTop - parseInt(delta * scrollDistance);

            gsap.to(window, {
                scrollTo: { y: finalScroll, autoKill: true },
                ease: Power1.easeOut,
                duration: scrollTime, // 추가된 부분: 애니메이션 지속 시간
                autoKill: true,
                overwrite: 5
            });
        });
    }, []);

    return (
        <>
            <header>
                <div className="inner"></div>
            </header>
            <section>
                <div className="profile-container">
                    {cards.map(card => (
                        <div key={card.id} className="profile-card">
                            <div className="profile-image-container">
                                {card.id === 1 && (
                                    <label className="file-upload-label">
                                        <input type="file" accept="image/*"
                                               onChange={(event) => handleImageChange(event, card.id)}/>
                                        {card.profileImage &&
                                            <img src={card.profileImage} alt="Profile" className="uploaded-image"/>}
                                        {!card.profileImage && <span>Upload Photo</span>}
                                    </label>
                                )}
                            </div>
                            {card.id === 1 && (
                                <textarea
                                    className="profile-input"
                                    placeholder="간단히 자기소개를 작성해주세요."
                                    value={card.bio}
                                    onChange={(event) => handleBioChange(event, card.id)}
                                ></textarea>
                            )}
                            {card.id === 2 && (
                                <div className="video-container">
                                    {[1, 2, 3].map((index) => (
                                        <label key={index}
                                               className={`file-upload-label video-upload-label ${index !== 2 ? 'video-upload-label-individual' : ''}`}>
                                            <input type="file" accept="video/*"
                                                   onChange={(event) => handleVideoChange(event, card.id, index)}/>
                                            {card.videos && card.videos[index] &&
                                                <video src={card.videos[index]} controls
                                                       className="uploaded-video"></video>}
                                            {!card.videos || !card.videos[index] && <span>Upload Video {index}</span>}
                                        </label>
                                    ))}
                                </div>
                            )}
                            {card.id === 3 && (
                                <div className="portfolio_contact">
                                    <img src="./portfolio_phone.png" alt="Phone Icon" className="contact-icon"/>
                                    <img src="./portfolio_email.png" alt="Email Icon" className="contact-icon"/>
                                    <label>Contact:</label>
                                    <input type="text" value={card.contact.email}
                                           onChange={(event) => handleContactChange(event, card.id, 'email')}
                                           placeholder="Email"/>
                                    <input type="text" value={card.contact.phone}
                                           onChange={(event) => handleContactChange(event, card.id, 'phone')}
                                           placeholder="Phone"/>
                                </div>
                            )}
                        </div>
                    ))}
                </div>
            </section>
            <Link to="/profile" className="profile-link">
                X
            </Link>
            <div className="boardname">
                <div className="col-md-12">
                    {/* 추가할거 추가하기 */}
                </div>
            </div>
        </>
    );
};

export default Portfolio;





/* 상단 사진크기 커지는 효과 넣은 코드 */

// import React, { useEffect, useState } from 'react';
// import gsap, { Power1 } from 'gsap';
// import ScrollToPlugin from 'gsap/ScrollToPlugin';
//
// import { Link } from "react-router-dom";
// import "./Portfolio.css";
//
// gsap.registerPlugin(ScrollToPlugin);
//
// const App = () => {
//     const [cards, setCards] = useState([
//         { id: 1, musicianName: "", profileImage: null, bio: "" },
//         { id: 2, musicianName: "", profileImage: null, videos: {} },
//         { id: 3, musicianName: "", profileImage: null, contact: { email: "", phone: "" } },
//         { id: 4, musicianName: "", profileImage: null, bio: "" },
//     ]);
//
//     const handleNameChange = (event, cardId) => {
//         const updatedCards = cards.map(card =>
//             card.id === cardId ? { ...card, musicianName: event.target.value } : card
//         );
//         setCards(updatedCards);
//     };
//
//     const handleImageChange = (event, cardId) => {
//         if (cardId === 1) {
//             const selectedImage = event.target.files[0];
//             const updatedCards = cards.map(card =>
//                 card.id === cardId ? { ...card, profileImage: URL.createObjectURL(selectedImage) } : card
//             );
//             setCards(updatedCards);
//         }
//     };
//
//     const handleBioChange = (event, cardId) => {
//         const updatedCards = cards.map(card =>
//             card.id === cardId ? { ...card, bio: event.target.value } : card
//         );
//         setCards(updatedCards);
//     };
//
//     const handleVideoChange = (event, cardId, videoIndex) => {
//         const selectedVideo = event.target.files[0];
//         if (cardId === 2) {
//             const updatedCards = cards.map(card =>
//                 card.id === cardId ? { ...card, videos: { ...card.videos, [videoIndex]: URL.createObjectURL(selectedVideo) } } : card
//             );
//             setCards(updatedCards);
//         }
//     };
//
//     const handleContactChange = (event, cardId, field) => {
//         const updatedCards = cards.map(card =>
//             card.id === cardId ? { ...card, contact: { ...card.contact, [field]: event.target.value } } : card
//         );
//         setCards(updatedCards);
//     };
//
//     useEffect(() => {
//         const inner = document.querySelector(".inner");
//         const section = document.querySelector("section");
//
//         window.onscroll = function () {
//             let value = window.pageYOffset / section.offsetTop + 1;
//             inner.style.transform = `scale(${value})`;
//         };
//
//         const $window = window;
//         const scrollTime = 1.2;
//         const scrollDistance = 170;
//
//         $window.addEventListener("wheel", function (event) {
//             event.preventDefault();
//
//             const delta = event.deltaY / 120 || -event.detail / 3;
//             const scrollTop = $window.scrollY;
//             const finalScroll = scrollTop - parseInt(delta * scrollDistance);
//
//             gsap.to(window, {
//                 scrollTo: { y: finalScroll, autoKill: true },
//                 ease: Power1.easeOut,
//                 autoKill: true,
//                 overwrite: 5
//             });
//         });
//     }, []);
//
//     return (
//         <>
//             <header>
//                 <div className="inner"></div>
//             </header>
//             <section>
//                 <div className="profile-container">
//                     {cards.map(card => (
//                         <div key={card.id} className="profile-card">
//                                 <div className="profile-image-container">
//                                     {card.id === 1 && (
//                                         <label className="file-upload-label">
//                                             <input type="file" accept="image/*"
//                                                    onChange={(event) => handleImageChange(event, card.id)}/>
//                                             {card.profileImage &&
//                                                 <img src={card.profileImage} alt="Profile" className="uploaded-image"/>}
//                                             {!card.profileImage && <span>Upload Photo</span>}
//                                         </label>
//                                     )}
//                                 </div>
//                                 {card.id === 1 && (
//                                     <textarea
//                                         className="profile-input"
//                                         placeholder="간단히 자기소개를 작성해주세요."
//                                         value={card.bio}
//                                         onChange={(event) => handleBioChange(event, card.id)}
//                                     ></textarea>
//                                 )}
//                                 {card.id === 2 && (
//                                     <div className="video-container">
//                                         {[1, 2, 3].map((index) => (
//                                             <label key={index}
//                                                    className={`file-upload-label video-upload-label ${index !== 2 ? 'video-upload-label-individual' : ''}`}>
//                                                 <input type="file" accept="video/*"
//                                                        onChange={(event) => handleVideoChange(event, card.id, index)}/>
//                                                 {card.videos && card.videos[index] &&
//                                                     <video src={card.videos[index]} controls
//                                                            className="uploaded-video"></video>}
//                                                 {!card.videos || !card.videos[index] && <span>Upload Video {index}</span>}
//                                             </label>
//                                         ))}
//                                     </div>
//                                 )}
//                             {card.id === 3 && (
//                                 <div className="portfolio_contact">
//                                     <img src="./portfolio_phone.png" alt="Phone Icon" className="contact-icon"/>
//                                     <img src="./portfolio_email.png" alt="Email Icon" className="contact-icon"/>
//                                     <label>Contact:</label>
//                                     <input type="text" value={card.contact.email}
//                                            onChange={(event) => handleContactChange(event, card.id, 'email')}
//                                            placeholder="Email"/>
//                                     <input type="text" value={card.contact.phone}
//                                            onChange={(event) => handleContactChange(event, card.id, 'phone')}
//                                            placeholder="Phone"/>
//                                 </div>
//                             )}
//                         </div>
//                     ))}
//                 </div>
//             </section>
//             <Link to="/profile" className="profile-link">
//                 X
//             </Link>
//             <div className="boardname">
//                 <div className="col-md-12">
//                     {/* 추가할거 추가하기 */}
//                 </div>
//             </div>
//         </>
//     );
// };
//
// export default App;
