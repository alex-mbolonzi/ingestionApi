import { Moment } from 'moment';

export interface IRequestStatusDetails {
  id?: number;
  requestStatusId?: number;
  decisionByName?: string;
  decisionByMudid?: string;
  decisionByEmail?: string;
  decisionDate?: Moment;
  decisionComments?: string;
  rejectionReason?: string;
  activeFlag?: boolean;
  createdBy?: string;
  createdDate?: Moment;
  modifiedBy?: string;
  modifiedDate?: Moment;
  ingestionRequestIdId?: number;
  statusIdStatusIdRef?: string;
  statusIdId?: number;
}

export class RequestStatusDetails implements IRequestStatusDetails {
  constructor(
    public id?: number,
    public requestStatusId?: number,
    public decisionByName?: string,
    public decisionByMudid?: string,
    public decisionByEmail?: string,
    public decisionDate?: Moment,
    public decisionComments?: string,
    public rejectionReason?: string,
    public activeFlag?: boolean,
    public createdBy?: string,
    public createdDate?: Moment,
    public modifiedBy?: string,
    public modifiedDate?: Moment,
    public ingestionRequestIdId?: number,
    public statusIdStatusIdRef?: string,
    public statusIdId?: number,
  ) {
    this.activeFlag = this.activeFlag || false;
  }
}
