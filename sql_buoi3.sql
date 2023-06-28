-- count *: đếm dòng
SELECT count(*) as tongso_gv
from giaovien;
-- count cột: đếm những giá trị của cột mà không null
SELECT count(GVQLCM) as tongso_gv
from giaovien;

select MABM, count(*) as soluong_gv
from GIAOVIEN 
group by MABM
having soluong_gv > 2;

-- tìm xem mỗi bộ môn có bao nhiêu giáo viên nam
select MABM, count(*) as sl
from giaovien 
where phai = 'Nam'
group by MABM;

-- tìm xem mỗi bộ môn có bao nhiêu giáo viên nam, hiển thị thêm thông tin khoa
select gv.MABM,k.TENKHOA, count(*) as sl
from giaovien gv join bomon bm on gv.MABM = bm.MABM join khoa k on k.MAKHOA = bm.MAKHOA
where phai = 'Nam'
group by MABM;

-- tìm xem mỗi bộ môn có bao nhiêu giáo viên nam nếu không có hiển thị là 0
SELECT *, (SELECT count(*)
			FROM giaovien where PHAI = 'NAM' and MABM = bm.MABM) as sl
FROM bomon bm; 

--  Cho biết những giáo viên có lương lớn hơn lương của giáo viên có MAGV=‘001’
SELECT *
FROM GIAOVIEN GV
WHERE GV.LUONG > (SELECT GV1.LUONG FROM GIAOVIEN GV1 WHERE GV1.MAGV = '001');

--  Cho biết những giáo viên có lương lớn hơn lương của giáo viên có MAGV=‘001’
SELECT *, (SELECT GV1.LUONG FROM GIAOVIEN GV1 WHERE GV1.MAGV = '001') AS LUONG001
FROM GIAOVIEN GV
WHERE GV.LUONG > (SELECT GV1.LUONG FROM GIAOVIEN GV1 WHERE GV1.MAGV = '001');

-- HIỂN THỊ NHỮNG GIÁO VIÊN KO CÓ NGƯỜI THÂN
SELECT *
FROM GIAOVIEN GV 
WHERE GV.MAGV NOT IN (SELECT DISTINCT MAGV FROM NGUOITHAN);


-- TÌM GIÁO VIÊN CÓ LƯƠNG NHỎ NHẤT
-- CÁCH 1:
SELECT *
FROM GIAOVIEN GV
WHERE GV.LUONG = (SELECT MIN(LUONG)
		FROM c03_quanlydetai.giaovien);
-- CÁCH 2 DÙNG ALL
SELECT *
FROM GIAOVIEN GV
WHERE GV.LUONG <= ALL (SELECT DISTINCT GV2.LUONG FROM GIAOVIEN GV2);

-- TÌM BỘ MÔN CÓ SỐ LƯỢNG GIÁO VIÊN ĐÔNG NHẤT
SELECT GV.MABM, COUNT(*)
FROM GIAOVIEN GV
GROUP BY GV.MABM
HAVING COUNT(*) >= ALL (
	SELECT COUNT(*)
	FROM GIAOVIEN GV
	GROUP BY GV.MABM
);
SELECT GV.MABM, COUNT(*)
FROM GIAOVIEN GV
GROUP BY GV.MABM
HAVING COUNT(*) = (
		SELECT MAX(TEMP.COUNT) AS MAX
		FROM (
			SELECT COUNT(*) AS COUNT
			FROM GIAOVIEN GV
			GROUP BY GV.MABM) AS TEMP
    );
    
-- Cho biết những giáo viên có lương lớn hơn lương trung bình của bộ môn mà giáo
-- viên đó làm việc
SELECT *, (SELECT AVG(GV2.LUONG) FROM GIAOVIEN GV2 WHERE GV2.MABM = GV.MABM) AS LTB
FROM giaovien GV
WHERE GV.LUONG > (SELECT AVG(GV2.LUONG) FROM GIAOVIEN GV2 WHERE GV2.MABM = GV.MABM);

-- CHO BIẾT THÔNG TIN CÁC TRƯỞNG BM CÓ THAM GIA ĐỀ TÀi
SELECT DISTINCT BM.TRUONGBM, GV.HOTEN, BM.TENBM
FROM BOMON BM JOIN THAMGIADT TGDT ON BM.TRUONGBM = TGDT.MAGV
	JOIN GIAOVIEN GV ON GV.MAGV = BM.TRUONGBM


