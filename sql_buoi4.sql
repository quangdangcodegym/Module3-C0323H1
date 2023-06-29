
drop procedure if exists  sp_ChonGiaoVienLuongLonHon;
delimiter //
create procedure sp_ChonGiaoVienLuongLonHon(
	IN pMaGV varchar(10)
)
begin
	SELECT *, (SELECT GV1.LUONG FROM GIAOVIEN GV1 WHERE GV1.MAGV = pMaGV) AS LUONG001
FROM GIAOVIEN GV
WHERE GV.LUONG > (SELECT GV1.LUONG FROM GIAOVIEN GV1 WHERE GV1.MAGV = pMaGV);
end; //

drop procedure if exists  sp_ThemGiaoVien;
delimiter //
create procedure sp_ThemGiaoVien(
	IN pHoTen varchar(40),
    IN pLuong decimal(10,1),
    IN pPhai varchar(3),
    IN pGVQLCM varchar(3),
    IN pMABM varchar(4),
    OUT pMAGV varchar(3),
    OUT pMessage varchar(50)
)
begin 
	declare flag boolean default true;
	if(not exists (select * from bomon where MABM = pMABM)) then
		SET flag = false;
	end if;
    if(not exists (select * from giaovien where MAGV = pGVQLCM)) then
		SET flag = false;
	end if;
    if(flag = true) then
			-- insert vào
            SET @pStrMaxID = (select MAGV from giaovien order by MAGV desc limit 1);	
            SET @pMaxtID = CAST(@pStrMaxID AS DECIMAL(3,0)) + 1;
            if(@pMaxtID > 0 and @pMaxtID <10) then 
				SET @pMaxtID = concat("00", @pMaxtID); 
                END IF;
			if(@pMaxtID >= 10 and @pMaxtID <100) then 
				SET @pMaxtID = concat("0", @pMaxtID); 
                END IF;
			INSERT INTO `giaovien` (`MAGV`, `HOTEN`, `LUONG`,  `GVQLCM`, `MABM`) 
            VALUES (@pMaxtID, pHoTen, pLuong, pGVQLCM, pMABM);
            SET pMAGV = @pMaxtID;
		else
			SET pMessage = 'Loi roi.......';
    end if;
end

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ThemGiaoVienAdvanced`(
IN pHoTen varchar(40),
    IN pLuong decimal(10,1),
    IN pPhai varchar(3),
    IN pGVQLCM varchar(3),
    IN pMABM varchar(4),
    OUT pMAGV varchar(3),
    OUT pMessage varchar(50)
)
begin 
	declare flag boolean default true;
	if(not exists (select * from bomon where MABM = pMABM)) then
		SET flag = false;
	end if;
    if(not exists (select * from giaovien where MAGV = pGVQLCM)) then
		SET flag = false;
	end if;
    if(flag = true) then
			-- insert vào
            set @pMaxtID = '0';
			call c03_quanlydetai.spGetMaxID(@pMaxID);
			INSERT INTO `giaovien` (`MAGV`, `HOTEN`, `LUONG`,  `GVQLCM`, `MABM`) 
            VALUES (@pMaxtID, pHoTen, pLuong, pGVQLCM, pMABM);
            SET pMAGV = @pMaxtID;
		else
			SET pMessage = 'Loi roi.......';
    end if;
end

CREATE DEFINER=`root`@`localhost` PROCEDURE `spGetMaxID`(
	OUT pMaxID varchar(3)
)
BEGIN
	SET @pStrMaxID = (select MAGV from giaovien order by MAGV desc limit 1);	
	SET @pMaxtID = CAST(@pStrMaxID AS DECIMAL(3,0)) + 1;
	if(@pMaxtID > 0 and @pMaxtID <10) then 
		SET @pMaxtID = concat("00", @pMaxtID); 
		END IF;
	if(@pMaxtID >= 10 and @pMaxtID <100) then 
		SET @pMaxtID = concat("0", @pMaxtID); 
		END IF;
	SET pMaxID = @pMaxtID;
END
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ChonGiaoVienLuongLonHon`(
	IN pMaGV varchar(10)
)
begin
	SELECT *, (SELECT GV1.LUONG FROM GIAOVIEN GV1 WHERE GV1.MAGV = pMaGV) AS LUONG001
FROM GIAOVIEN GV
WHERE GV.LUONG > (SELECT GV1.LUONG FROM GIAOVIEN GV1 WHERE GV1.MAGV = pMaGV);
end


