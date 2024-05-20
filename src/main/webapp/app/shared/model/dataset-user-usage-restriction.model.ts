import { Moment } from 'moment';

export interface IDatasetUserUsageRestriction {
  id?: number;
  usageUserRestrictionId?: number;
  restrictionRef?: string;
  restrictionTypeRef?: string;
  reasonForOther?: string;
  createdBy?: string;
  createdDate?: Moment;
  modifiedBy?: string;
  modifiedDate?: Moment;
  datasetIdId?: number;
}

export class DatasetUserUsageRestriction implements IDatasetUserUsageRestriction {
  constructor(
    public id?: number,
    public usageUserRestrictionId?: number,
    public restrictionRef?: string,
    public restrictionTypeRef?: string,
    public reasonForOther?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public modifiedBy?: string,
    public modifiedDate?: Moment,
    public datasetIdId?: number,
  ) {}
}
