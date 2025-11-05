const form = document.getElementById("registerForm");
const message = document.getElementById("message");

form.addEventListener("submit", async (e) => {
    e.preventDefault();
    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());

    try {
        const response = await fetch("/api/auth/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        let result = null;
        const text = await response.text();
        console.log(text);
        if (text) {
            try {
                result = JSON.parse(text);
            } catch {
                console.warn("Ответ не JSON:", text);
            }
        }
        console.log(result);

        if (response.ok) {
            message.style.color = "green";

            // Используем accessToken вместо token
            if (result?.accessToken) {
                message.textContent = `Регистрация успешна! Токен сохранён.`;

                // Сохраняем токен в localStorage
                localStorage.setItem("jwtToken", result.accessToken);

                // Опционально можно сохранить refreshToken
                localStorage.setItem("refreshToken", result.refreshToken);
            } else {
                message.textContent = "Регистрация успешна!";
            }

            form.reset();
        } else {
            message.style.color = "red";
            message.textContent = result?.message || "Ошибка регистрации";
        }

    } catch (err) {
        message.style.color = "red";
        message.textContent = "Сервер недоступен";
        console.error(err);
    }
});
