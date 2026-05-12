import React, { useState, useEffect } from 'react';

// Mock Data for Dropdowns
const mockData = {
  divisions: ['Division A', 'Division B', 'Division C'],
  engineers: [
    { id: 1, name: 'John Doe' },
    { id: 2, name: 'Jane Smith' },
    { id: 3, name: 'Bob Johnson' }
  ],
  stkCusts: [
    { id: 1, value: 'Stock' },
    { id: 2, value: 'Customer' }
  ],
  regions: ['North', 'South', 'East', 'West'],
  branches: [
    { id: 1, name: 'Branch 1' },
    { id: 2, name: 'Branch 2' }
  ],
  dealers: [
    { id: 1, name: 'Dealer X' },
    { id: 2, name: 'Dealer Y' }
  ],
  suppliers: [
    { id: 1, name: 'Supplier A' },
    { id: 2, name: 'Supplier B' }
  ],
  models: [
    { id: 1, name: 'Model S' },
    { id: 2, name: 'Model X' }
  ],
  unitStatuses: [
    { id: 5, value: 'Status 5' },
    { id: 7, value: 'Status 7' },
    { id: 43, value: 'Status 43 (DOD req)' },
    { id: 44, value: 'Status 44 (DOD req)' },
    { id: 1, value: 'Other Status' }
  ],
  defTypes: [
    { id: 1, value: 'Defect Type A' },
    { id: 2, value: 'Defect Type B' }
  ],
  typeOfAccs: [
    { id: 1, value: 'Account 1' },
    { id: 2, value: 'Account 2' }
  ],
  repTypes: [
    { id: 56, value: 'Rep Type 56' },
    { id: 60, value: 'Rep Type 60' }
  ],
  workTypes: [
    { id: 23, value: 'Work 23' },
    { id: 25, value: 'Work 25' }
  ]
};

const ServiceFormPOC = () => {
  // Form State
  const [formData, setFormData] = useState({
    division: '',
    scRefNo: '',
    scEngineer: '',
    frnNo: '',
    frnDate: '',
    serCommInwardDate: '',
    receiveDateSerCentre: '',
    stkCust: '',
    region: '',
    branch: '',
    engineer: '',
    dealerName: '',
    custName: '',
    supplier: '',
    model: '',
    modelConfig: '',
    unitSlNo: '',
    unitStatus: '',
    dod: '',
    modName: '',
    partNo: '',
    compModels: '',
    defName: '',
    cost: '',
    defType: '',
    typeAcc: '',
    defSnNo: '',
    defGirNo: '',
    repType: '',
    repGirSno: '',
    defUnitGirNo: '',
    fieldRemarks: '',
    techRemarks: '',
    compRepair: '',
    finalRemarks: '',
    invoice: '',
    workType: '',
    shipDate: '',
    dispAdviceNo: '',
    repairStkDate: '',
    repairedBrdAdvNo: '',
    commercialDate: '',
    dcNo: '',
    reportType: '',
    destination: ''
  });

  // UI Rules State (Required, Readonly, Visibility)
  const [uiState, setUiState] = useState({
    dodRequired: false,
    repGirRequired: false,
    shipDateRequired: false,
    repairStkDateRequired: false,
    commercialDateRequired: false,
    dcNoRequired: false,
    
    repGirStar: false,
    shipDateStar: false,
    repairStkDateStar: false,
    commercialDateStar: false,
    dcNoStar: false,

    destinationReadonly: false,
    shipDateReadonly: false,
    repairStkDateReadonly: false,
    commercialDateReadonly: false,
    dcNoReadonly: false,
    dispAdviceNoReadonly: true,
    repairedBrdAdvNoReadonly: true,

    dispAdviceNoRequired: false,
    repairedBrdAdvNoRequired: false,

    reportOptions: [
      { value: 'servicecenter', label: 'SERVICE CENTER' },
      { value: 'scraplist', label: 'SCRAP LIST' },
      { value: 'dispatchlist', label: 'DISPATCH LIST' },
      { value: 'stocklist', label: 'STOCK LIST' }
    ]
  });

  // Handle Input Change
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  // Replicate JSP Logic Scripts
  useEffect(() => {
    let newState = { ...uiState };
    
    // -- Select() Logic (Unit Status) --
    if (formData.unitStatus === '43' || formData.unitStatus === '44') {
      newState.dodRequired = true;
    } else {
      newState.dodRequired = false;
    }

    // -- shipDate() / commercialDate() / repairedDate() --
    if (formData.shipDate) {
      newState.dispAdviceNoRequired = true;
      newState.dispAdviceNoReadonly = false;
    } else {
      newState.dispAdviceNoRequired = false;
      newState.dispAdviceNoReadonly = true;
    }
    
    if (formData.commercialDate) {
      newState.dcNoRequired = true;
      newState.dcNoReadonly = false;
    } else {
      newState.dcNoRequired = false;
      newState.dcNoReadonly = true;
    }

    if (formData.repairStkDate) {
      newState.repairedBrdAdvNoRequired = true;
      newState.repairedBrdAdvNoReadonly = false;
    } else {
      newState.repairedBrdAdvNoRequired = false;
      newState.repairedBrdAdvNoReadonly = true;
    }

    // -- changefun1(), changefun_scrapp(), changefn(), changefun3() --
    // We emulate the cascading overrides
    const repTypeNum = parseInt(formData.repType);
    const workTypeNum = parseInt(formData.workType);
    const gir = formData.repGirSno || '';
    const gir1 = formData.defGirNo || '';

    // Default resets for complex logic
    newState.repGirStar = false;
    newState.shipDateStar = false;
    newState.repairStkDateStar = false;
    newState.commercialDateStar = false;
    newState.dcNoStar = false;
    
    let allowedOpts = ['servicecenter', 'scraplist', 'dispatchlist', 'stocklist'];

    // If repType == 56 logic
    if (repTypeNum === 56 && gir === '') {
      newState.repGirStar = false;
      newState.repGirRequired = false;
      allowedOpts = ['servicecenter'];
      newState.shipDateReadonly = true;
      newState.repairStkDateReadonly = true;
      newState.commercialDateReadonly = true;
      newState.dcNoReadonly = true;
    } else if (repTypeNum === 56 && gir !== '' && gir.toUpperCase() !== gir1.toUpperCase()) {
      newState.repGirStar = true;
      newState.repairStkDateReadonly = true;
      newState.shipDateReadonly = false;
      newState.commercialDateReadonly = true;
      newState.dcNoReadonly = true;

      newState.repGirRequired = true;
      newState.shipDateRequired = true;
      newState.repairStkDateRequired = false;
      newState.commercialDateRequired = false;
      newState.dcNoRequired = false;

      newState.shipDateStar = true;
      allowedOpts = ['dispatchlist'];
    } else if (repTypeNum === 56 && gir !== '' && gir.toUpperCase() === gir1.toUpperCase()) {
      newState.repGirStar = true;
      newState.repairStkDateReadonly = false;
      newState.shipDateReadonly = false;
      newState.commercialDateReadonly = true;
      newState.dcNoReadonly = true;

      newState.repGirRequired = true;
      newState.shipDateRequired = true;
      newState.repairStkDateRequired = true;
      newState.commercialDateRequired = false;
      newState.dcNoRequired = false;

      newState.repGirStar = true;
      newState.shipDateStar = true;
      newState.repairStkDateStar = true;
      allowedOpts = ['dispatchlist'];
    }

    // If repType == 60 logic
    if (repTypeNum === 60) {
      if (gir === '') {
        newState.repairStkDateReadonly = true;
        newState.shipDateReadonly = true;
        newState.commercialDateReadonly = true;
        newState.dcNoReadonly = true;

        allowedOpts = ['servicecenter'];
        
        newState.repGirRequired = true;
        newState.shipDateRequired = true;
        newState.repairStkDateRequired = false;
        newState.commercialDateRequired = true;
        newState.dcNoRequired = true;

        newState.repGirStar = true;
        newState.shipDateStar = true;
        newState.commercialDateStar = true;
        newState.dcNoStar = true;
      } else {
        newState.shipDateReadonly = false;
        newState.repairStkDateReadonly = true;
        newState.commercialDateReadonly = false;
        newState.dcNoReadonly = false;

        allowedOpts = ['scraplist'];

        newState.repGirRequired = true;
        newState.shipDateRequired = true;
        newState.repairStkDateRequired = false;
        newState.commercialDateRequired = true;
        newState.dcNoRequired = true;

        newState.repGirStar = true;
        newState.shipDateStar = true;
        newState.commercialDateStar = true;
        newState.dcNoStar = true;
      }
      
      // Override from changefun_scrapp() if workType affects repType 60
      if (workTypeNum === 23) {
        newState.repairStkDateReadonly = false;
        newState.shipDateReadonly = false;
        newState.commercialDateReadonly = false;
        newState.dcNoReadonly = false;
        allowedOpts = ['scraplist'];

        newState.repairStkDateRequired = true;
        newState.repairStkDateStar = true;
      } else if (workTypeNum === 25 || (workTypeNum && workTypeNum !== 25)) {
        allowedOpts = ['servicecenter'];
        newState.repairStkDateReadonly = true;
        newState.repairStkDateRequired = false;
        newState.repairStkDateStar = false;
      }
    }

    // Filter report options
    const fullOpts = [
      { value: 'servicecenter', label: 'SERVICE CENTER' },
      { value: 'scraplist', label: 'SCRAP LIST' },
      { value: 'dispatchlist', label: 'DISPATCH LIST' },
      { value: 'stocklist', label: 'STOCK LIST' }
    ];
    // In JSP, it shows/hides options. If allowedOpts is restricted, only show those.
    // We'll leave all options available if no conditions met.
    if (allowedOpts.length > 0) {
       newState.reportOptions = fullOpts.filter(o => allowedOpts.includes(o.value) || allowedOpts.length === 4);
    }

    setUiState(newState);
  }, [
    formData.unitStatus, formData.repType, formData.repGirSno, 
    formData.defGirNo, formData.workType, formData.shipDate, 
    formData.commercialDate, formData.repairStkDate
  ]);

  const handleSubmit = (e) => {
    e.preventDefault();
    // Validate unit_status 5 or 7 requiring DOD
    if ((formData.unitStatus === '5' || formData.unitStatus === '7') && !formData.dod) {
      alert('DOD Column is Mandatory !!');
      return;
    }
    console.log('Form Submitted', formData);
    alert('Form submitted successfully!');
  };

  return (
    <div className="container" style={{ marginTop: '20px' }}>
      <div className="row">
        <div className="col-lg-12">
          <h3 className="page-header">Add Service</h3>
          <button className="btn btn-success" style={{ marginBottom: '15px' }}>Get Previous</button>
        </div>
      </div>
      
      <div className="panel panel-default">
        <div className="panel-heading">Enter the details of the Service</div>
        <div className="panel-body">
          <form role="form" onSubmit={handleSubmit}>
            <div className="row">
              {/* --- COLUMN 1 --- */}
              <div className="col-lg-4">
                <div className="form-group required">
                  <label>Division</label>
                  <select name="division" className="form-control" value={formData.division} onChange={handleChange}>
                    <option value="">Select</option>
                    {mockData.divisions.map((d, i) => <option key={i} value={d}>{d}</option>)}
                  </select>
                </div>

                <div className="form-group required">
                  <label>SC_REF_NO <span style={{ color: 'red' }}>*</span></label>
                  <input type="text" name="scRefNo" className="form-control" value={formData.scRefNo} onChange={handleChange} required />
                </div>

                <div className="form-group required">
                  <label>SC ENGINEER <span style={{ color: 'red' }}>*</span></label>
                  <select className="form-control" name="scEngineer" value={formData.scEngineer} onChange={handleChange} required>
                    <option value="">Select Engineer</option>
                    {mockData.engineers.map((e) => <option key={e.id} value={e.id}>{e.name}</option>)}
                  </select>
                </div>

                <div className="form-group required">
                  <label>FRN NO <span style={{ color: 'red' }}>*</span></label>
                  <input type="text" name="frnNo" className="form-control" value={formData.frnNo} onChange={handleChange} required />
                </div>

                <div className="form-group">
                  <label>FRN DATE <span style={{ color: 'red' }}>*</span></label>
                  <input type="date" name="frnDate" className="form-control" value={formData.frnDate} onChange={handleChange} required />
                </div>

                <div className="form-group">
                  <label>SER COMM INWARD DATE <span style={{ color: 'red' }}>*</span></label>
                  <input type="date" name="serCommInwardDate" className="form-control" value={formData.serCommInwardDate} onChange={handleChange} required />
                </div>

                <div className="form-group">
                  <label>RECEIVED DATE SER CENTRE <span style={{ color: 'red' }}>*</span></label>
                  <input type="date" name="receiveDateSerCentre" className="form-control" value={formData.receiveDateSerCentre} onChange={handleChange} required />
                </div>

                <div className="form-group">
                  <label>STK/CUST <span style={{ color: 'red' }}>*</span></label>
                  <select className="form-control" name="stkCust" value={formData.stkCust} onChange={handleChange} required>
                    <option value="">Select</option>
                    {mockData.stkCusts.map((e) => <option key={e.id} value={e.id}>{e.value}</option>)}
                  </select>
                </div>

                <div className="form-group">
                  <label>SELECT REGION <span style={{ color: 'red' }}>*</span></label>
                  <select className="form-control" name="region" value={formData.region} onChange={handleChange} required>
                    <option value="">Select Region</option>
                    {mockData.regions.map((r, i) => <option key={i} value={r}>{r}</option>)}
                  </select>
                </div>

                <div className="form-group">
                  <label>BRANCH <span style={{ color: 'red' }}>*</span></label>
                  <select className="form-control" name="branch" value={formData.branch} onChange={handleChange} required>
                    <option value="">Select Branch</option>
                    {mockData.branches.map((b) => <option key={b.id} value={b.id}>{b.name}</option>)}
                  </select>
                </div>

                <div className="form-group">
                  <label>ENGINEER <span style={{ color: 'red' }}>*</span></label>
                  <select className="form-control" name="engineer" value={formData.engineer} onChange={handleChange}>
                    <option value="">Select Engineer</option>
                    {mockData.engineers.map((e) => <option key={e.id} value={e.id}>{e.name}</option>)}
                  </select>
                </div>

                <div className="form-group">
                  <label>DEALER NAME <span style={{ color: 'red' }}>*</span></label>
                  <select className="form-control" name="dealerName" value={formData.dealerName} onChange={handleChange} required>
                    <option value="">Select Dealer</option>
                    {mockData.dealers.map((d) => <option key={d.id} value={d.id}>{d.name}</option>)}
                  </select>
                </div>

                <div className="form-group">
                  <label>CUSTOMER NAME <span style={{ color: 'red' }}>*</span></label>
                  <input type="text" name="custName" className="form-control" value={formData.custName} onChange={handleChange} required />
                </div>

                <div className="form-group">
                  <label>SUPPLIER <span style={{ color: 'red' }}>*</span></label>
                  <select className="form-control" name="supplier" value={formData.supplier} onChange={handleChange} required>
                    <option value="">Select Supplier</option>
                    {mockData.suppliers.map((s) => <option key={s.id} value={s.id}>{s.name}</option>)}
                  </select>
                </div>
              </div>

              {/* --- COLUMN 2 --- */}
              <div className="col-lg-4">
                <div className="form-group">
                  <label>MODEL <span style={{ color: 'red' }}>*</span></label>
                  <select className="form-control" name="model" value={formData.model} onChange={handleChange} required>
                    <option value="">Select Model</option>
                    {mockData.models.map((m) => <option key={m.id} value={m.id}>{m.name}</option>)}
                  </select>
                </div>

                <div className="form-group">
                  <label>Model Configuration</label>
                  <input type="text" name="modelConfig" className="form-control" value={formData.modelConfig} onChange={handleChange} />
                </div>

                <div className="form-group">
                  <label>UNIT SL NO <span style={{ color: 'red' }}>*</span></label>
                  <input type="text" name="unitSlNo" className="form-control" value={formData.unitSlNo} onChange={handleChange} required />
                </div>

                <div className="form-group">
                  <label>UNIT STATUS <span style={{ color: 'red' }}>*</span></label>
                  <select className="form-control" name="unitStatus" value={formData.unitStatus} onChange={handleChange} required>
                    <option value="">Select Status</option>
                    {mockData.unitStatuses.map((u) => <option key={u.id} value={u.id}>{u.value}</option>)}
                  </select>
                </div>

                <div className="form-group">
                  <label>DOD {uiState.dodRequired && <span style={{ color: 'red' }}>*</span>}</label>
                  <input type="date" name="dod" className="form-control" value={formData.dod} onChange={handleChange} required={uiState.dodRequired} />
                </div>

                <div className="form-group">
                  <label>MOD/BRD NAME <span style={{ color: 'red' }}>*</span></label>
                  <input type="text" name="modName" className="form-control" value={formData.modName} onChange={handleChange} required />
                </div>

                <div className="form-group">
                  <label>PART NUMBER</label>
                  <input type="text" name="partNo" className="form-control" value={formData.partNo} onChange={handleChange} />
                </div>

                <div className="form-group">
                  <label>COMPATIBLE MODELS</label>
                  <input type="text" name="compModels" className="form-control" value={formData.compModels} onChange={handleChange} />
                </div>

                <div className="form-group">
                  <label>DEF MOD/BRD NAME <span style={{ color: 'red' }}>*</span></label>
                  <input type="text" name="defName" className="form-control" value={formData.defName} onChange={handleChange} required />
                </div>

                <div className="form-group">
                  <label>Cost</label>
                  <input type="text" name="cost" className="form-control" value={formData.cost} onChange={handleChange} />
                </div>

                <div className="form-group">
                  <label>DEF TYPE <span style={{ color: 'red' }}>*</span></label>
                  <select className="form-control" name="defType" value={formData.defType} onChange={handleChange} required>
                    <option value="">Select DEF Type</option>
                    {mockData.defTypes.map((d) => <option key={d.id} value={d.id}>{d.value}</option>)}
                  </select>
                </div>

                <div className="form-group">
                  <label>TYPE OF ACC <span style={{ color: 'red' }}>*</span></label>
                  <select className="form-control" name="typeAcc" value={formData.typeAcc} onChange={handleChange} required>
                    <option value="">Select Account</option>
                    {mockData.typeOfAccs.map((a) => <option key={a.id} value={a.id}>{a.value}</option>)}
                  </select>
                </div>

                <div className="form-group">
                  <label>DEF PART SNO <span style={{ color: 'red' }}>*</span></label>
                  <input type="text" name="defSnNo" className="form-control" value={formData.defSnNo} onChange={handleChange} required />
                </div>

                <div className="form-group">
                  <label>DEF GIR NO <span style={{ color: 'red' }}>*</span></label>
                  <input type="text" name="defGirNo" className="form-control" value={formData.defGirNo} onChange={handleChange} required />
                </div>

                <div className="form-group">
                  <label>REP TYPE <span style={{ color: 'red' }}>*</span></label>
                  <select className="form-control" name="repType" value={formData.repType} onChange={handleChange} required>
                    <option value="">Select Rep type</option>
                    {mockData.repTypes.map((r) => <option key={r.id} value={r.id}>{r.value}</option>)}
                  </select>
                </div>
              </div>

              {/* --- COLUMN 3 --- */}
              <div className="col-lg-4">
                <div className="form-group">
                  <label>REP GIR SNO {uiState.repGirStar && <span style={{ color: 'red' }}>*</span>}</label>
                  <input type="text" name="repGirSno" className="form-control" value={formData.repGirSno} onChange={handleChange} required={uiState.repGirRequired} />
                </div>

                <div className="form-group">
                  <label>DEF UNIT GIR NO</label>
                  <input type="text" name="defUnitGirNo" className="form-control" value={formData.defUnitGirNo} onChange={handleChange} />
                </div>

                <div className="form-group">
                  <label>FIELD REMARKS <span style={{ color: 'red' }}>*</span></label>
                  <input type="text" name="fieldRemarks" className="form-control" value={formData.fieldRemarks} onChange={handleChange} required />
                </div>

                <div className="form-group">
                  <label>TECHNICAL REMARKS</label>
                  <input type="text" name="techRemarks" className="form-control" value={formData.techRemarks} onChange={handleChange} />
                </div>

                <div className="form-group">
                  <label>COMPONENTS USED TO REPAIR</label>
                  <input type="text" name="compRepair" className="form-control" value={formData.compRepair} onChange={handleChange} />
                </div>

                <div className="form-group">
                  <label>FINAL REMARKS</label>
                  <input type="text" name="finalRemarks" className="form-control" value={formData.finalRemarks} onChange={handleChange} />
                </div>

                <div className="form-group">
                  <label>COMMERCIAL DETAILS INVOICE REMARKS</label>
                  <input type="text" name="invoice" className="form-control" value={formData.invoice} onChange={handleChange} />
                </div>

                <div className="form-group">
                  <label>TYPE OF WORK/STATUS <span style={{ color: 'red' }}>*</span></label>
                  <select className="form-control" name="workType" value={formData.workType} onChange={handleChange} required>
                    <option value="">Select Work</option>
                    {mockData.workTypes.map((w) => <option key={w.id} value={w.id}>{w.value}</option>)}
                  </select>
                </div>

                <div className="form-group">
                  <label>SHIP DATE FROM SERVICE CENTER {uiState.shipDateStar && <span style={{ color: 'red' }}>*</span>}</label>
                  <input type="date" name="shipDate" className="form-control" value={formData.shipDate} onChange={handleChange} required={uiState.shipDateRequired} readOnly={uiState.shipDateReadonly} />
                </div>

                {/* Optional additional fields that were hidden/readonly based on dates */}
                {/* 
                <div className="form-group">
                  <label>DISP ADVICE NO {uiState.dispAdviceNoRequired && <span style={{color:'red'}}>*</span>}</label>
                  <input type="text" name="dispAdviceNo" className="form-control" value={formData.dispAdviceNo} onChange={handleChange} required={uiState.dispAdviceNoRequired} readOnly={uiState.dispAdviceNoReadonly} />
                </div> 
                */}

                <div className="form-group">
                  <label>REPAIRED BRD STK DATE {uiState.repairStkDateStar && <span style={{ color: 'red' }}>*</span>}</label>
                  <input type="date" name="repairStkDate" className="form-control" value={formData.repairStkDate} onChange={handleChange} required={uiState.repairStkDateRequired} readOnly={uiState.repairStkDateReadonly} />
                </div>

                {/* 
                <div className="form-group">
                  <label>STOCK ADV NO {uiState.repairedBrdAdvNoRequired && <span style={{color:'red'}}>*</span>}</label>
                  <input type="text" name="repairedBrdAdvNo" className="form-control" value={formData.repairedBrdAdvNo} onChange={handleChange} required={uiState.repairedBrdAdvNoRequired} readOnly={uiState.repairedBrdAdvNoReadonly} />
                </div> 
                */}

                <div className="form-group">
                  <label>SHIP DATE FROM COMMERCIAL {uiState.commercialDateStar && <span style={{ color: 'red' }}>*</span>}</label>
                  <input type="date" name="commercialDate" className="form-control" value={formData.commercialDate} onChange={handleChange} required={uiState.commercialDateRequired} readOnly={uiState.commercialDateReadonly} />
                </div>

                <div className="form-group">
                  <label>DC NO {uiState.dcNoStar && <span style={{ color: 'red' }}>*</span>}</label>
                  <input type="text" name="dcNo" className="form-control" value={formData.dcNo} onChange={handleChange} required={uiState.dcNoRequired} readOnly={uiState.dcNoReadonly} />
                </div>

                <div className="form-group">
                  <label>TYPE OF REPORT <span style={{ color: 'red' }}>*</span></label>
                  <select className="form-control" name="reportType" value={formData.reportType} onChange={handleChange} required>
                    <option value="">Select Report</option>
                    {uiState.reportOptions.map((ro) => (
                      <option key={ro.value} value={ro.value}>{ro.label}</option>
                    ))}
                  </select>
                </div>

                <div className="form-group">
                  <label>DESTINATION <span style={{ color: 'red' }}>*</span></label>
                  <input type="text" name="destination" className="form-control" value={formData.destination} onChange={handleChange} required readOnly={uiState.destinationReadonly} />
                </div>

                <div style={{ paddingRight: '20px' }}>
                  <button type="submit" className="btn btn-success" style={{ marginTop: '25px', float: 'right' }}>SAVE</button>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default ServiceFormPOC;
