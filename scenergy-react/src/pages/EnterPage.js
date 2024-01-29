import React, { useEffect, useState, useRef } from "react";
import styles from "./EnterPage.module.css";
import { Link } from "react-router-dom";

const EnterPage = () => {
  const [stars, setStars] = useState([]);
  const [meteor, setMeteor] = useState(false);
  const constelacaoRef = useRef(null); // .constelacao 요소 참조

  useEffect(() => {
    function init() {
      // .constelacao width height 가져오기
      const constelacaoWidth = constelacaoRef.current.offsetWidth;
      const constelacaoHeight = constelacaoRef.current.offsetHeight;

      // 별 생성
      const starStyles = ["style1", "style2", "style3", "style4"];
      const starSizes = ["tam1", "tam1", "tam1", "tam2", "tam3"];
      const starOpacities = [
        "opacity1",
        "opacity1",
        "opacity1",
        "opacity2",
        "opacity2",
        "opacity3",
      ];
      const starElements = [];
      const starCount = 250;

      for (let i = 0; i < starCount; i++) {
        const styleIndex = getRandomArbitrary(0, 4);
        const opacityIndex = getRandomArbitrary(0, 6);
        const sizeIndex = getRandomArbitrary(0, 5);

        const starElement = (
          <span
            key={i}
            className={`${styles.estrela} ${styles[starStyles[styleIndex]]} ${
              styles[starOpacities[opacityIndex]]
            } ${styles[starSizes[sizeIndex]]}`}
            style={{
              animationDelay: `.${getRandomArbitrary(0, 9)}s`,
              left: `${getRandomArbitrary(0, constelacaoWidth)}px`, // 수정된 부분
              top: `${getRandomArbitrary(0, constelacaoHeight)}px`, // 수정된 부분
            }}
          ></span>
        );

        starElements.push(starElement);
      }

      setStars(starElements);

      // 유성 생성
      const meteorInterval = setInterval(
        () => {
          setMeteor(true);
          setTimeout(() => {
            setMeteor(false);
          }, 500);
        },
        getRandomArbitrary(1000, 3000),
      );

      return () => clearInterval(meteorInterval);
    }

    init();
  }, []);

  function getRandomArbitrary(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
  }

  return (
    <div className={styles.noite}>
      <div ref={constelacaoRef} className={styles.constelacao}>
        {stars}
      </div>
      {meteor && (
        <div
          className={`${styles.meteoro} ${styles[`style${getRandomArbitrary(0, 4)}`]}`}
        ></div>
      )}
      <div className={styles.lua}>
        <div className={styles.textura}></div>
      </div>
      <div className={styles.EnterBtn}>
        <div className={styles.EnterBtnLink}>
            <Link to="/home">입장하기</Link>
            <Link to="/home">시너지</Link>
        </div>
      </div>
      <div className={styles.floresta}>
        {/*<img*/}
        {/*    src="https://raw.githubusercontent.com/interaminense/starry-sky/master/src/img/bgTree.png"*/}
        {/*    alt=""*/}
        {/*/>*/}
      </div>
    </div>
  );
};

export default EnterPage;
