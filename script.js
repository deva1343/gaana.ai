document.addEventListener('DOMContentLoaded', () => {
    // Handle sign-up form submission
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
                const response = await fetch('https://b2b6-103-82-43-28.ngrok-free.app/signup', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                });
                const result = await response.json();
                console.log(result);
                if (result.status === 'User created') {
                    alert('Sign-up successful!');
                    window.location.href = 'sign-in.html'; // Redirect to sign-in page
                } else {
                    alert(result.error || 'Sign-up failed');
                }
            } catch (error) {
                console.error('Error:', error);
                alert('An error occurred. Please try again.');
            }
        });
    }

    // Handle login form submission
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
                const response = await fetch('https://b2b6-103-82-43-28.ngrok-free.app/login', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                });
                const result = await response.json();
                console.log(result);
                if (result.status === 'Login successful') {
                    alert('Login successful!');
                    window.location.href = 'index.html'; // Redirect to the main page
                } else {
                    alert(result.error || 'Login failed');
                }
            } catch (error) {
                console.error('Error:', error);
                alert('An error occurred. Please try again.');
            }
        });
    }

    // Fetch users data (for testing purposes)
    const fetchUsers = async () => {
        try {
            const response = await fetch('https://b2b6-103-82-43-28.ngrok-free.app/users');
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

    // Example call to fetch users data
    fetchUsers();
});
