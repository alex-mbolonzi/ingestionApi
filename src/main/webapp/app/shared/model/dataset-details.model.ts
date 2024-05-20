import { Moment } from 'moment';
import { IDatasetDataCategory } from 'app/shared/model/dataset-data-category.model';
import { IDatasetIndication } from 'app/shared/model/dataset-indication.model';
import { IDatasetRoleDetails } from 'app/shared/model/dataset-role-details.model';
import { IDatasetStudy } from 'app/shared/model/dataset-study.model';
import { IDatasetTechAndAssay } from 'app/shared/model/dataset-tech-and-assay.model';
import { IDatasetTherapy } from 'app/shared/model/dataset-therapy.model';
import { IDatasetUserUsageRestriction } from 'app/shared/model/dataset-user-usage-restriction.model';

export interface IDatasetDetails {
  id?: number;
  datasetId?: number;
  datasetName?: string;
  datasetOriginSource?: string;
  currentDataLocationRef?: string;
  meteorSpaceDominoUsageFlag?: boolean;
  ihdFlag?: boolean;
  datasetRequiredForRef?: string;
  estimatedDataVolumeRef?: string;
  analysisInitDt?: Moment;
  analysisEndDt?: Moment;
  dtaContractCompleteFlag?: boolean;
  dtaExpectedCompletionDate?: Moment;
  datasetTypeRef?: string;
  informationClassificationTypeRef?: string;
  piiTypeRef?: string;
  retentionRules?: string;
  retentionRulesContractDate?: Moment;
  contractPartner?: string;
  createdBy?: string;
  createdDate?: Moment;
  modifiedBy?: string;
  modifiedDate?: Moment;
  ingestionRequestIdId?: number;
  datasetDataCategories?: IDatasetDataCategory[];
  datasetIndications?: IDatasetIndication[];
  datasetRoleDetails?: IDatasetRoleDetails[];
  datasetStudies?: IDatasetStudy[];
  datasetTechAndAssays?: IDatasetTechAndAssay[];
  datasetTherapies?: IDatasetTherapy[];
  datasetUserUsageRestrictions?: IDatasetUserUsageRestriction[];
}

export class DatasetDetails implements IDatasetDetails {
  constructor(
    public id?: number,
    public datasetId?: number,
    public datasetName?: string,
    public datasetOriginSource?: string,
    public currentDataLocationRef?: string,
    public meteorSpaceDominoUsageFlag?: boolean,
    public ihdFlag?: boolean,
    public datasetRequiredForRef?: string,
    public estimatedDataVolumeRef?: string,
    public analysisInitDt?: Moment,
    public analysisEndDt?: Moment,
    public dtaContractCompleteFlag?: boolean,
    public dtaExpectedCompletionDate?: Moment,
    public datasetTypeRef?: string,
    public informationClassificationTypeRef?: string,
    public piiTypeRef?: string,
    public retentionRules?: string,
    public retentionRulesContractDate?: Moment,
    public contractPartner?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public modifiedBy?: string,
    public modifiedDate?: Moment,
    public ingestionRequestIdId?: number,
    public datasetDataCategories?: IDatasetDataCategory[],
    public datasetIndications?: IDatasetIndication[],
    public datasetRoleDetails?: IDatasetRoleDetails[],
    public datasetStudies?: IDatasetStudy[],
    public datasetTechAndAssays?: IDatasetTechAndAssay[],
    public datasetTherapies?: IDatasetTherapy[],
    public datasetUserUsageRestrictions?: IDatasetUserUsageRestriction[],
  ) {
    this.meteorSpaceDominoUsageFlag = this.meteorSpaceDominoUsageFlag || false;
    this.ihdFlag = this.ihdFlag || false;
    this.dtaContractCompleteFlag = this.dtaContractCompleteFlag || false;
  }
}
