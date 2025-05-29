

const LoginText = () => {
    return(
        <div>
            <h2>LOGIN</h2>
        </div>
    );
}

const InputField = ({name, type, placeholder, required, value, onchange}) => {
    return(
        <div style={styles.container}>
            <input
                name={name}
                type={type}
                placeholder={placeholder}
                required={required}
                style={styles.input}
                value={value}
                onChange={onchange}
            />
        </div>
    );
}



const SubmitButton = () => {
    return(
        <div style={styles.button}>
            <button>Submit</button>
        </div>

    );

}

const styles = {
    input: {
        padding: '10px',
        fontSize: '16px',

    },
    button: {
        padding: '10px',
        color: 'white',
        fontSize: '16px',
        border: 'none'
    },
    error: {
        color: 'red',
        marginTop: '10px'
    }
};

export {LoginText, InputField, SubmitButton }
