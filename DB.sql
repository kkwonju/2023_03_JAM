
# a5 데이터베이스 삭제/생성/선택
DROP DATABASE IF EXISTS `a5`;
CREATE DATABASE `a5`;
USE `a5`;
# 부서(dept) 테이블 생성 및 홍보부서 기획부서 추가
CREATE TABLE dept(
  id INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100)
  );

INSERT
INTO dept
SET `name` = '홍보';

INSERT
INTO dept
SET `name` = '기획';

SELECT *
FROM dept;
# 사원(emp) 테이블 생성 및 홍길동사원(홍보부서), 홍길순사원(홍보부서), 임꺽정사원(기획부서) 추가
CREATE TABLE emp(
  id INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,  
  `name` VARCHAR(100),
  deptName VARCHAR(100)
  );

INSERT
INTO emp
SET
 `name` = '홍길동',
 deptName = '홍보';


INSERT
INTO emp
SET
 `name` = '홍길순',
 deptName = '홍보';

INSERT
INTO emp
SET
 `name` = '임꺽정',
 deptName = '기획';

SELECT *
FROM emp;

# 홍보를 마케팅으로 변경
UPDATE emp
SET deptName = '마케팅'
WHERE deptName = '홍보';


# 마케팅을 홍보로 변경
UPDATE emp
SET deptName = '홍보'
WHERE deptName = '마케팅';

# 홍보를 마케팅으로 변경
UPDATE emp
SET deptName = '마케팅'
WHERE deptName = '홍보';

# 구조를 변경하기로 결정(사원 테이블에서, 이제는 부서를 이름이 아닌 번호로 기억)
ALTER TABLE emp ADD COLUMN deptId INT(10) NOT NULL AFTER deptName;

UPDATE emp
SET deptId = 1
WHERE deptName = '마케팅';

UPDATE emp
SET deptId = 2
WHERE deptName = '기획';

# 사장님께 드릴 인명록을 생성
ALTER TABLE emp DROP COLUMN deptName;

SELECT *
FROM emp;

# 사장님께서 부서번호가 아니라 부서명을 알고 싶어하신다.
# 그래서 dept 테이블 조회법을 알려드리고 혼이 났다.
SELECT *
FROM dept;

SELECT *
FROM emp;

# 사장님께 드릴 인명록을 생성(v2, 부서명 포함, ON 없이)
# 이상한 데이터가 생성되어서 혼남
SELECT *
FROM emp
INNER JOIN dept;

# 사장님께 드릴 인명록을 생성(v3, 부서명 포함, 올바른 조인 룰(ON) 적용)
# 보고용으로 좀 더 편하게 보여지도록 고쳐야 한다고 지적받음
SELECT emp.*, dept.name
FROM emp
INNER JOIN dept
ON dept.id = emp.deptId;


# 사장님께 드릴 인명록을 생성(v4, 사장님께서 보시기에 편한 칼럼명(AS))
SELECT 
 emp.id AS '사원번호',
 emp.name AS '사원명',
 dept.name AS '부서명'
FROM emp
INNER JOIN dept
ON dept.id = emp.deptId;




# a6 DB 삭제/생성/선택
DROP DATABASE IF EXISTS `a6`;
CREATE DATABASE `a6`;
USE `a6`;

# 부서(홍보, 기획)
CREATE TABLE dept(
    id INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL
    );
    
INSERT
INTO dept
SET `name` = '홍보';

INSERT
INTO dept
SET `name` = '기획';

SELECT *
FROM dept;
# 사원(홍길동/홍보/5000만원, 홍길순/홍보/6000만원, 임꺽정/기획/4000만원)
CREATE TABLE emp(
    id INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    deptName VARCHAR(100) NOT NULL,
    salary VARCHAR(100) NOT NULL
    );
    
INSERT
INTO emp
SET
 `name` = '홍길동',
 deptName = '홍보',
 salary = '5000만원';


INSERT
INTO emp
SET
 `name` = '홍길순',
 deptName = '홍보',
 salary = '6000만원';
 
INSERT
INTO emp
SET
 `name` = '임꺽정',
 deptName = '기획',
 salary = '4000만원';

SELECT *
FROM emp;

# 사원 수 출력
SELECT COUNT(id) AS '사원 수'
FROM emp;
# 가장 큰 사원 번호 출력 
SELECT MAX(id) AS '가장 큰 사원 번호'
FROM emp;
# 가장 고액 연봉
SELECT MAX(salary) AS '가장 고액 연봉'
FROM emp;
# 가장 저액 연봉
SELECT MIN(salary) AS '가장 저액 연봉'
FROM emp;
# 회사에서 1년 고정 지출(인건비)
SELECT SUM(salary) AS '1년 고정 지출(인건비)'
FROM emp;
# 부서별, 1년 고정 지출(인건비)
SELECT emp.deptName,SUM(salary) AS '1년 고정 지출(인건비)'
FROM emp
GROUP BY deptName DESC;
# 부서별, 최고연봉
SELECT emp.deptName, MAX(salary) AS '최고연봉'
FROM emp
GROUP BY deptName;
# 부서별, 최저연봉
SELECT emp.deptName, MIN(salary) AS '최저연봉'
FROM emp
GROUP BY deptName;
# 부서별, 평균연봉
SELECT emp.deptName, AVG(salary) AS '평균연봉'
FROM emp
GROUP BY deptName;
# 부서별, 부서명, 사원리스트, 평균연봉, 최고연봉, 최소연봉, 사원수 
## V1(조인 안한 버전)

SELECT *
FROM emp;

ALTER TABLE emp CHANGE deptName deptId INT(10) NOT NULL;

UPDATE emp
SET deptId = 1
WHERE deptId = 0
LIMIT 2;

UPDATE emp
SET deptId = 2
WHERE deptId = 0;


SELECT
  emp.deptId AS '부서명',
  GROUP_CONCAT(emp.name) AS '사원리스트',
  CONCAT(AVG(emp.salary),'만원') AS '평균연봉',
  MAX(emp.salary) AS '최고연봉',
  MIN(emp.salary) AS '최저연봉',
  COUNT(emp.id) AS '사원 수'
  FROM emp
  GROUP BY emp.deptId;

  
## V2(조인해서 부서명까지 나오는 버전)
SELECT
  dept.name AS '부서명',
  GROUP_CONCAT(emp.name) AS '사원리스트',
  CONCAT(AVG(emp.salary),'만원') AS '평균연봉',
  MAX(emp.salary) AS '최고연봉',
  MIN(emp.salary) AS '최저연봉',
  COUNT(emp.id) AS '사원 수'
FROM emp
INNER JOIN dept
ON dept.id = emp.deptId
GROUP BY emp.deptId;
  
## V3(V2에서 평균연봉이 5000이상인 부서로 추리기) ? 
SELECT
  dept.name AS '부서명',
  GROUP_CONCAT(emp.name) AS '사원리스트',
  CONCAT(AVG(emp.salary),'만원') AS '평균연봉',
  MAX(emp.salary) AS '최고연봉',
  MIN(emp.salary) AS '최저연봉',
  COUNT(emp.id) AS '사원 수'
FROM emp
INNER JOIN dept
ON dept.id = emp.deptId
GROUP BY emp.deptId
HAVING 평균연봉 >= 5000;

## V4(V3에서 HAVING 없이 서브쿼리로 수행)
### HINT, UNION을 이용한 서브쿼리
# SELECT *
# FROM (
#     select 1 AS id
#     union
#     select 2
#     UNION
#     select 3
# ) AS A
