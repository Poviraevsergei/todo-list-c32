SELECT * FROM users INNER JOIN security ON users.id = security.user_id;
SELECT * FROM users LEFT JOIN security ON users.id = security.user_id;
SELECT * FROM users RIGHT JOIN security ON users.id = security.user_id;
SELECT * FROM users FULL JOIN security ON users.id = security.user_id;
SELECT * FROM users CROSS JOIN security;

----

CREATE OR REPLACE FUNCTION find_password_by_login(login_input varchar)
    RETURNS varchar
    LANGUAGE plpgsql
AS
'
    DECLARE
        password_result varchar;
    BEGIN
        SELECT password INTO password_result FROM security WHERE login = login_input;
        return password_result;
    END;
';

SELECT find_password_by_login('login_max');

----

CREATE OR REPLACE PROCEDURE delete_user_by_firstname(firstname_input varchar)
    LANGUAGE plpgsql
AS
'
    BEGIN
        DELETE FROM users WHERE  firstname = firstname_input;
    END;
';

CALL delete_user_by_firstname('Bill2')