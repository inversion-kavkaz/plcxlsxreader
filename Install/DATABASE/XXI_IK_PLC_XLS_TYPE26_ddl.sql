-- Start of DDL Script for Table XXI.IK_PLC_XLS_TYPE26

CREATE TABLE ik_plc_xls_type26
    (id                             NUMBER NOT NULL,
    cusrlogname                    VARCHAR2(30 BYTE) NOT NULL,
    cfio                           VARCHAR2(255 BYTE) NOT NULL,
    cinn                           VARCHAR2(12 BYTE) NOT NULL,
    caccc                          VARCHAR2(20 BYTE) NOT NULL,
    msumm                          NUMBER(10,2) NOT NULL,
    ipaydate                       NUMBER NOT NULL,
    caccd                          VARCHAR2(20 BYTE) NOT NULL)
/

-- Grants for Table
GRANT DELETE ON ik_plc_xls_type26 TO odb
/
GRANT INSERT ON ik_plc_xls_type26 TO odb
/
GRANT UPDATE ON ik_plc_xls_type26 TO odb
/


-- Indexes for IK_PLC_XLS_TYPE26

CREATE INDEX ik_plc_xls_type26_usr_nuk ON ik_plc_xls_type26
(
    cusrlogname                     ASC
)
/



-- Constraints for IK_PLC_XLS_TYPE26

ALTER TABLE ik_plc_xls_type26
ADD CONSTRAINT ik_plc_xls_type26_pk PRIMARY KEY (id, cusrlogname)
/


-- Comments for IK_PLC_XLS_TYPE26

COMMENT ON TABLE ik_plc_xls_type26 IS 'BVV 2020/09/11
����������� ����� �������� �� XLSX
'
/
COMMENT ON COLUMN ik_plc_xls_type26.caccc IS '���� ����������'
/
COMMENT ON COLUMN ik_plc_xls_type26.caccd IS '���� ��������'
/
COMMENT ON COLUMN ik_plc_xls_type26.cfio IS '���'
/
COMMENT ON COLUMN ik_plc_xls_type26.cinn IS '���'
/
COMMENT ON COLUMN ik_plc_xls_type26.cusrlogname IS '������������'
/
COMMENT ON COLUMN ik_plc_xls_type26.id IS '��������� ����'
/
COMMENT ON COLUMN ik_plc_xls_type26.ipaydate IS '���� ������� (�����)'
/
COMMENT ON COLUMN ik_plc_xls_type26.msumm IS '�����'
/

-- End of DDL Script for Table XXI.IK_PLC_XLS_TYPE26
create public SYNONYM ik_plc_xls_type26 for ik_plc_xls_type26
/
