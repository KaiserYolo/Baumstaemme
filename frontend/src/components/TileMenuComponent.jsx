import React from 'react';
import WoodBox from "./WoodBox.jsx";
import '../App.css';

export function TileMenuTitleComponent({name, isTitle}) {
    return (
        (isTitle === "true" ?
            <div className="overlay-title">
                {name}
            </div>
            :
            <div className="overlay-subtitle">
                {name}
            </div>
        )
    )
}

export function TileMenuResourceComponent({valueName, value}) {
    return (
        <div>
            {valueName}: {value}
        </div>
    )
}

export function TileMenuComponent({valueName, value, buttonFunction,width, buttonText}) {
    return (
        <div className="overlay-text-with-function">
            <label className="overlay-label">
                {valueName}: {value}
            </label>
            <button className="test-button" onClick={buttonFunction}>
                <WoodBox n={width} text={buttonText} />
            </button>
        </div>
    )
}



