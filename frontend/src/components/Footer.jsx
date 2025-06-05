import WoodBox from "./WoodBox.jsx";

export default function Footer() {
    return (
        <footer className="footer">
            <div className="footer-content">
                <button className="impressum-button">
                    <WoodBox n={6} text="Impressum" />
                </button>
            </div>
        </footer>
    );
}