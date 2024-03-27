//import React, { useState } from 'react';
import '../styles/Main.css';
import { Routes, Route} from 'react-router-dom';
import Login from '../pages/Login';
import Join from '../pages/Join';
import MyInfo from '../pages/MyInfo';
import MList from '../pages/MList';
import MainDefault from '../pages/MainDefault';

function Main({ token, onLoginSubmit }) {
    return (
        <div className="body_container">
        <Routes>
            <Route path="/login" 
                   element={<Login onLoginSubmit={onLoginSubmit}/>}/>
            <Route path="/join" element={<Join />} />
            <Route path="/myinfo" element={<MyInfo token={token} />} />
            <Route path="/mlist" element={<MList token={token} />} />
            <Route path="/" element={<MainDefault />} />
        </Routes>
        </div>
    );
}

export default Main;
