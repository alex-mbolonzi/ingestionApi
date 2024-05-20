import { Moment } from 'moment';
import { IRequestStatusDetails } from 'app/shared/model/request-status-details.model';
import { IValidationNotes } from 'app/shared/model/validation-notes.model';

export interface IIngestionRequestDetails {
  id?: number;
  ingestionRequestId?: number;
  requesterName?: string;
  requesterMudid?: string;
  requesterEmail?: string;
  requestRationaleReason?: string;
  requestedByName?: string;
  requestedByMudid?: string;
  requestedByEmail?: string;
  createdBy?: string;
  createdDate?: Moment;
  modifiedBy?: string;
  modifiedDate?: Moment;
  modifiedReason?: string;
  datasetDetailsId?: number;
  technicalDetailsId?: number;
  requestStatusDetails?: IRequestStatusDetails[];
  validationNotes?: IValidationNotes[];
}

export class IngestionRequestDetails implements IIngestionRequestDetails {
  constructor(
    public id?: number,
    public ingestionRequestId?: number,
    public requesterName?: string,
    public requesterMudid?: string,
    public requesterEmail?: string,
    public requestRationaleReason?: string,
    public requestedByName?: string,
    public requestedByMudid?: string,
    public requestedByEmail?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public modifiedBy?: string,
    public modifiedDate?: Moment,
    public modifiedReason?: string,
    public datasetDetailsId?: number,
    public technicalDetailsId?: number,
    public requestStatusDetails?: IRequestStatusDetails[],
    public validationNotes?: IValidationNotes[],
  ) {}
}
