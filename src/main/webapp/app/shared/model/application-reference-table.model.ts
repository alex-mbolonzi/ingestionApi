import { Moment } from 'moment';

export interface IApplicationReferenceTable {
  id?: number;
  referenceId?: number;
  referenceData?: string;
  referenceDataType?: string;
  createdBy?: string;
  createdDate?: Moment;
  modifiedBy?: string;
  modifiedDate?: Moment;
  referenceOrder?: number;
  referenceGroupType?: string;
}

export class ApplicationReferenceTable implements IApplicationReferenceTable {
  constructor(
    public id?: number,
    public referenceId?: number,
    public referenceData?: string,
    public referenceDataType?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public modifiedBy?: string,
    public modifiedDate?: Moment,
    public referenceOrder?: number,
    public referenceGroupType?: string,
  ) {}
}
