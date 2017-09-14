set define off;

create or replace procedure edit_CC (oper in varchar2, iccn in varchar2, iccn_code in varchar2, 
                                      icc_name in varchar2, icard_type in varchar2, iexp_date in date, 
                                      istreet1 in varchar2, istreet2 in varchar2, icity in varchar2, 
                                      istate in varchar2, izip in varchar2) as
begin

if oper = 'ins' then
      insert into aahmed31.cc
      values (iccn, 
            iccn_code, 
            icc_name, 
            icard_type, 
            iexp_date, 
            istreet1,
            istreet2, 
            icity, 
            istate, 
            izip);
       end if;
            
if oper = 'del' then
      delete from aahmed31.cc
              where ccn = iccn;
        end if;

if oper = 'upd' then
      update aahmed31.cc
      set ccn_code = iccn_code, 
          cc_name = icc_name, 
          card_type = icard_type, 
          exp_date = iexp_date, 
          street1 = istreet1,
          street2 = istreet2, 
          city = icity, 
          state = istate, 
          zip = izip
      where ccn = iccn;
  end if;
      
end edit_CC;