import Form from '../components/Form.jsx';
import '../App.css';

export default function LoginPage({onLogin}) {
    return (
        <main className="main">
            <div className="main-content" >
                <Form onLogin={onLogin}/>
            </div>
        </main>
    );
}