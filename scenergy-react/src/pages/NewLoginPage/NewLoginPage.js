import React, {useState} from 'react';
import styles from './NewLoginPage.module.css';
import {AnimateOnChange} from 'react-animation';
import NextButton from "./Button";
import {useNavigate} from "react-router-dom";
import backvid from './backvid.mp4';
import logo from './logo.png';

const firstPage = (onClick) => {
    return (
        <div>
            <div className={styles.firstBack}>
                <h1>
                    가입하고
                    <br/>
                    수많은 뮤지션들을
                    <br/>
                    만나보세요
                    <br/>
                </h1>
                <div style={{height: '100px'}}></div>
                <div className={styles.center}>
                    <NextButton onClick={() => onClick()}>
                        가입하기
                    </NextButton>
                </div>
            </div>
        </div>
    );
};
const secondPage = (onClick) => {
    return (
        <div>
            <div className={styles.back}>
                <img src={logo} className={styles.logo} alt=""/>
            </div>
            <div className={styles.center}>
                <NextButton onClick={() => {
                    window.location.href = `${process.env.REACT_APP_API_URL}/oauth2/authorization/naver`
                }}>
                    네이버로 로그인
                </NextButton>
            </div>
        </div>
    );
};
const LoginPage = () => {
    const [count, setCount] = useState(0);
    const navigate = useNavigate();
    return (
        <div className={`${styles.mainContainer}`}>
            <video className={styles.video} autoPlay loop muted>
                <source src={backvid} type='video/mp4'/>
            </video>
            <AnimateOnChange
                durationOut={500}
            >
                {
                    count === 0 ? firstPage(() => setCount(count + 1)) :
                        secondPage(() =>
                            window.location.href = `${process.env.REACT_APP_API_URL}/oauth2/authorization/naver`
                        )
                }
            </AnimateOnChange>
        </div>
    );
};

export default LoginPage;
