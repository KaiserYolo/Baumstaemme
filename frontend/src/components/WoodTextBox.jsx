import React from 'react';
import '../app.css';

import left from "../assets/ui/box_left.png";
import mid from "../assets/ui/box_mid.png";
import right from "../assets/ui/box_right.png";

export default function WoodTextBox({ n = 1, text = ''}) {
    const middleImages = Array.from({ length: n });

    return (
        <div className="woodbox">
            <img src={left} alt={`left`} className="image" />
            <div className="middle-container">
                {middleImages.map((_, index) => (
                    <img
                        key={index}
                        src={mid}
                        alt={`mid ${index + 1}`}
                        className="image"
                    />
                ))}
                <div className="middle-text">{text}</div>
            </div>
            <img src={right} alt={`right`} className="image" />
        </div>
    );
}