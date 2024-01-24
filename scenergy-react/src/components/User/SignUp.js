import React, { useState } from 'react';
import './SignUp.css';

const SignUp = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
    confirmPassword: '',
    name: '',
    nickname: '',
    birthdate: '',
    gender: '',
  });

  const [pwMsg, setPwMsg] = useState('');
  const [pwCheckMsg, setPwCheckMsg] = useState('');
  const [birthdateMsg, setBirthdateMsg] = useState('');

  const validatePw = (password) => {
    return password.toLowerCase().match(/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name === 'password') {
      setFormData((prevData) => ({ ...prevData, [name]: value }));

      if (!validatePw(value)) {
        setPwMsg('영문, 숫자, 특수기호 조합 8자리 이상');
      } else {
        setPwMsg('');
      }
    } else if (name === 'confirmPassword') {
      setFormData((prevData) => ({ ...prevData, [name]: value }));

      const isPasswordMatch = value === formData.password;
      setPwCheckMsg(
        isPasswordMatch
          ? ''
          : '비밀번호가 일치하지 않습니다.'
      );
    } else if (name === 'birthdate') {
      setFormData((prevData) => ({ ...prevData, [name]: value }));

      if (!/^\d{8}$/.test(value)) {
        setBirthdateMsg('숫자 8자리를 입력하세요.');
      } else {
        setBirthdateMsg('');
      }
    } else {
      setFormData((prevData) => ({ ...prevData, [name]: value }));
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('회원가입 정보:', formData);
  };

  return (
    <div className="signup-container">
      <h1>회원가입</h1>
      <form onSubmit={handleSubmit}>
        <label>
          이메일 :
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
            placeholder="이메일을 입력하세요"
          />
        </label>
        <br />

        <label>
          비밀번호 :
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            required
            placeholder="비밀번호을 입력하세요"
          />
          {pwMsg && <p style={{ color: 'red' }}>{pwMsg}</p>}
        </label>
        <br />

        <label>
          비밀번호 확인 :
          <input
            type="password"
            name="confirmPassword"
            value={formData.confirmPassword}
            onChange={handleChange}
            required
            placeholder="비밀번호 확인"
          />
          {pwCheckMsg && <p style={{ color: 'red' }}>{pwCheckMsg}</p>}
        </label>
        <br />

        <label>
          이름 :
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
            placeholder="실명을 입력하세요"
          />
        </label>
        <br />

        <label>
          닉네임 :
          <input
            type="text"
            name="nickname"
            value={formData.nickname}
            onChange={handleChange}
            required
            placeholder="닉네임을 입력하세요"
          />
        </label>
        <br />

        <label>
          생년월일 (8자리) :
          <input
            type="text"
            name="birthdate"
            value={formData.birthdate}
            onChange={handleChange}
            maxLength="8"
            required
            placeholder="생년월일 8자리 입력"
          />
          {birthdateMsg && <p style={{ color: 'red' }}>{birthdateMsg}</p>}
        </label>
        <br />

        <label>
          성별 : 
            {/* <label> */}
              <input
                type="radio"
                name="gender"
                value="male"
                checked={formData.gender === 'male'}
                onChange={handleChange}
              />
              남성
            {/* </label> */}
            {/* <label> */}
              <input
                type="radio"
                name="gender"
                value="female"
                checked={formData.gender === 'female'}
                onChange={handleChange}
              />
              여성
            {/* </label> */}
        </label>
        <br />

        <button type="submit">회원가입</button>
      </form>
    </div>
  );
};

export default SignUp;
