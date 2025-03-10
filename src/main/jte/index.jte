<!doctype html>
<html>
<head>
    <title>Biblioteka - admin</title>
</head>
<body>
    <h1>Witaj w panelu zarządzania biblioteką!</h1>
    @if(org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication() == null || org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
        <a href="/login">Zaloguj</a>
    @else
        <a href="/books">Książki</a>
        <a href="/authors">Autorzy</a>
        <a href="/customers">Klienci</a>
        <a href="/borrows">Wypożyczenia</a>
    @endif
</body>
</html>