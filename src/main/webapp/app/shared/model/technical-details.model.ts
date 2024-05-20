import { Moment } from 'moment';

export interface ITechnicalDetails {
  id?: number;
  technicalRequestId?: number;
  dataLocationPath?: string;
  dataRefreshFrequency?: string;
  targetIngestionStartDate?: Moment;
  targetIngestionEndDate?: Moment;
  targetPath?: string;
  datasetTypeIngestionRef?: string;
  guestUsersEmail?: string;
  whitelistIpAddresses?: string;
  externalStagingContainerName?: string;
  externalDataSourceLocation?: string;
  gskAccessSourceLocationRef?: string;
  externalSourceSecretKeyName?: string;
  domainRequestId?: string;
  existingDataLocationIdentified?: string;
  createdBy?: string;
  createdDate?: Moment;
  modifiedBy?: string;
  modifiedDate?: Moment;
  ingestionRequestIdId?: number;
}

export class TechnicalDetails implements ITechnicalDetails {
  constructor(
    public id?: number,
    public technicalRequestId?: number,
    public dataLocationPath?: string,
    public dataRefreshFrequency?: string,
    public targetIngestionStartDate?: Moment,
    public targetIngestionEndDate?: Moment,
    public targetPath?: string,
    public datasetTypeIngestionRef?: string,
    public guestUsersEmail?: string,
    public whitelistIpAddresses?: string,
    public externalStagingContainerName?: string,
    public externalDataSourceLocation?: string,
    public gskAccessSourceLocationRef?: string,
    public externalSourceSecretKeyName?: string,
    public domainRequestId?: string,
    public existingDataLocationIdentified?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public modifiedBy?: string,
    public modifiedDate?: Moment,
    public ingestionRequestIdId?: number,
  ) {}
}
