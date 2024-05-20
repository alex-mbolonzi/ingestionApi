import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { DatasetDetailsService } from 'app/entities/dataset-details/dataset-details.service';
import { IDatasetDetails, DatasetDetails } from 'app/shared/model/dataset-details.model';

describe('Service Tests', () => {
  describe('DatasetDetails Service', () => {
    let injector: TestBed;
    let service: DatasetDetailsService;
    let httpMock: HttpTestingController;
    let elemDefault: IDatasetDetails;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DatasetDetailsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DatasetDetails(
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        false,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        false,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        currentDate,
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            analysisInitDt: currentDate.format(DATE_FORMAT),
            analysisEndDt: currentDate.format(DATE_FORMAT),
            dtaExpectedCompletionDate: currentDate.format(DATE_FORMAT),
            retentionRulesContractDate: currentDate.format(DATE_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            modifiedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a DatasetDetails', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            analysisInitDt: currentDate.format(DATE_FORMAT),
            analysisEndDt: currentDate.format(DATE_FORMAT),
            dtaExpectedCompletionDate: currentDate.format(DATE_FORMAT),
            retentionRulesContractDate: currentDate.format(DATE_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            modifiedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            analysisInitDt: currentDate,
            analysisEndDt: currentDate,
            dtaExpectedCompletionDate: currentDate,
            retentionRulesContractDate: currentDate,
            createdDate: currentDate,
            modifiedDate: currentDate,
          },
          returnedFromService,
        );
        service
          .create(new DatasetDetails(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a DatasetDetails', () => {
        const returnedFromService = Object.assign(
          {
            datasetId: 1,
            datasetName: 'BBBBBB',
            datasetOriginSource: 'BBBBBB',
            currentDataLocationRef: 'BBBBBB',
            meteorSpaceDominoUsageFlag: true,
            ihdFlag: true,
            datasetRequiredForRef: 'BBBBBB',
            estimatedDataVolumeRef: 'BBBBBB',
            analysisInitDt: currentDate.format(DATE_FORMAT),
            analysisEndDt: currentDate.format(DATE_FORMAT),
            dtaContractCompleteFlag: true,
            dtaExpectedCompletionDate: currentDate.format(DATE_FORMAT),
            datasetTypeRef: 'BBBBBB',
            informationClassificationTypeRef: 'BBBBBB',
            piiTypeRef: 'BBBBBB',
            retentionRules: 'BBBBBB',
            retentionRulesContractDate: currentDate.format(DATE_FORMAT),
            contractPartner: 'BBBBBB',
            createdBy: 'BBBBBB',
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            modifiedBy: 'BBBBBB',
            modifiedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );

        const expected = Object.assign(
          {
            analysisInitDt: currentDate,
            analysisEndDt: currentDate,
            dtaExpectedCompletionDate: currentDate,
            retentionRulesContractDate: currentDate,
            createdDate: currentDate,
            modifiedDate: currentDate,
          },
          returnedFromService,
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of DatasetDetails', () => {
        const returnedFromService = Object.assign(
          {
            datasetId: 1,
            datasetName: 'BBBBBB',
            datasetOriginSource: 'BBBBBB',
            currentDataLocationRef: 'BBBBBB',
            meteorSpaceDominoUsageFlag: true,
            ihdFlag: true,
            datasetRequiredForRef: 'BBBBBB',
            estimatedDataVolumeRef: 'BBBBBB',
            analysisInitDt: currentDate.format(DATE_FORMAT),
            analysisEndDt: currentDate.format(DATE_FORMAT),
            dtaContractCompleteFlag: true,
            dtaExpectedCompletionDate: currentDate.format(DATE_FORMAT),
            datasetTypeRef: 'BBBBBB',
            informationClassificationTypeRef: 'BBBBBB',
            piiTypeRef: 'BBBBBB',
            retentionRules: 'BBBBBB',
            retentionRulesContractDate: currentDate.format(DATE_FORMAT),
            contractPartner: 'BBBBBB',
            createdBy: 'BBBBBB',
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            modifiedBy: 'BBBBBB',
            modifiedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            analysisInitDt: currentDate,
            analysisEndDt: currentDate,
            dtaExpectedCompletionDate: currentDate,
            retentionRulesContractDate: currentDate,
            createdDate: currentDate,
            modifiedDate: currentDate,
          },
          returnedFromService,
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body),
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DatasetDetails', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
