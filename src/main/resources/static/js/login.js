alert("a")
var app = new Vue({
    el: "#app",
    data: {
      isLogin: true
    },
    template:
    `
    <form action="isLogin? login : register" method="post">
        <h3>Введите данные:</h3>
    
        <div><label for="username"><b>Username</b></label></div>
        <div><input type="text" name="username" id="username" required></div>
    
        <div><label for="password"><b>Password</b></label></div>
        <div><input type="password" name="password" id="password" required></div>
    
        <div><button type="submit">Подтвердить</button></div>
        
    </form>
    <div><button @click="isLogin = !isLogin">isLogin? "Зарегистрироваться" : "Войти" </button></div>
    `
})