fetch('https://b2b6-103-82-43-28.ngrok-free.app/users')
  .then(response => response.json())
  .then(data => {
    console.log(data);
    // Code to display users data on the webpage
    let usersList = document.getElementById('users');
    usersList.innerHTML = data.map(user => `<li>${user.name} (${user.email})</li>`).join('');
  })
  .catch(error => console.error('Error:', error));
