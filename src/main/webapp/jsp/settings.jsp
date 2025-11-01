<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <fieldset>
            <legend>Пароль</legend>
            <form method="post" action="/settings">
                <div>
                    <label>
                        Текущий пароль:
                        <input type="password" name="current_password" minlength="8" maxlength="255" required />
                    </label>
                </div>
                <div>
                    <label>
                        Новый пароль:
                        <input type="password" name="new_password" minlength="8" maxlength="255" required />
                    </label>
                </div>
                <div>
                    <label>
                        Повторите пароль:
                        <input type="password" name="repeat_password" minlength="8" maxlength="255" required />
                    </label>
                </div>
                <div>
                    <input type="submit" value="Изменить пароль">
                </div>
            </form>
        </fieldset>