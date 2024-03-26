//import React, { useState } from 'react';
import '../styles/Main.css';
import { Routes, Route} from 'react-router-dom';
import Login from '../pages/Login';
import Join from '../pages/Join';
import MainDefault from '../pages/MainDefault';

function Main({ setIsLoggedIn, onLoginSubmit }) {
    return (
        <div className="body_container">
        <Routes>
            <Route path="/login" 
                   element={<Login onLoginSubmit={onLoginSubmit}/>}/>
            <Route path="/join" element={<Join />} />
            <Route path="/" element={<MainDefault />} />
        </Routes>
        </div>
    );
}

export default Main;
