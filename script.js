document.addEventListener('DOMContentLoaded', () => {
    const signUpForm = document.getElementById('signupForm');
    if (signUpForm) {
        signUpForm.addEventListener('submit', async (event) => {
            event.preventDefault();
            const formData = new FormData(signUpForm);
            const data = {
                username: formData.get('username'),
                email: formData.get('email'),
                password: formData.get('password')
            };

            try {
                const response = await fetch('https://YOUR_NGROK_URL/signup', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                });

                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }

                const result = await response.json();
                console.log(result);

                if (result.status === 'User created') {
                    alert('Sign-up successful!');
                    window.location.href = 'sign-in.html';
                } else {
                    alert(result.error || 'Sign-up failed');
                }
            } catch (error) {
                console.error('Error:', error);
                alert('An error occurred. Please try again.');
            }
        });
    }

    const loginForm = document.getElementById('signinForm');
    if (loginForm) {
        loginForm.addEventListener('submit', async (event) => {
            event.preventDefault();
            const formData = new FormData(loginForm);
            const data = {
                email: formData.get('email'),
                password: formData.get('password')
            };

            try {
                const response = await fetch('https://YOUR_NGROK_URL/login', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                });

                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }

                const result = await response.json();
                console.log(result);

                if (result.status === 'Login successful') {
                    alert('Login successful!');
                    window.location.href = 'index.html';
                } else {
                    alert(result.error || 'Login failed');
                }
            } catch (error) {
                console.error('Error:', error);
                alert('An error occurred. Please try again.');
            }
        });
    }

    const fetchUsers = async () => {
        try {
            const response = await fetch('https://YOUR_NGROK_URL/users');

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();
            console.log(data);

            let usersList = document.getElementById('users');
            if (usersList) {
                usersList.innerHTML = data.map(user => `<li>${user.username} (${user.email})</li>`).join('');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    // fetchUsers(); // Uncomment if needed
});
