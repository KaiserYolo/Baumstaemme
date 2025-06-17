import '../App.css';
import WoodBox from './WoodBox';

export default function Header({onLoginClick, onTitleClick}) {
    return (
        <header className="header">
            <div className="header-content">
                <button className="title-button" onClick={onTitleClick}>
                    <WoodBox n={9} text="Die Baumstämme" className="header-title" />
                </button>
                <button className="login-button" onClick={onLoginClick}>
                    <WoodBox n={4} text="Login" />
                </button>
            </div>
        </header>
    )
}